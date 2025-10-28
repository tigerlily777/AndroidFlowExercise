# Android FLow

## 🌊 第一步：What is Flow？

官方是这样介绍的：

Flow 是用于异步数据流的 Kotlin 类型。它是 cold stream，也就是说它只有在被 collect 的时候才会运行。
📦 举个例子（咱们先看一个简单的 Flow）：

```kotlin
fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // 模拟异步操作
        emit(i)    // 发出一个值
    }
}
```

然后在 main 函数或 coroutine 中收集它：

```kotlin
runBlocking {
    simple().collect { value ->
        println(value)
    }
}
```

🧠 类比一下就像：
你是一位老板（Flow），你会定时安排员工（数据）来汇报工作（emit），但只有当你在办公室（collect）的时候，他们才会过来，不然他们就一直待机等着～

## 🌪 第二步：Flow vs suspend function

✅ suspend 函数：

返回单个结果，例如：

```kotlin:
suspend fun getUser(): User
```

✅ Flow：

返回多个结果，数据是一点一点来的，像这样：

```kotlin
fun getUsers(): Flow<User>
```

## 🧩 第三步：Flow builder

官方列举了几种 Flow 构建方式，常用的有：
flow {}: 手动发射值，常见于复杂异步操作
flowOf(): 直接创建固定值流
asFlow(): 把集合转成 flow

✨ 示例：

```kotlin
fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(1000)
        emit(i)
    }
}

fun rangeAsFlow(): Flow<Int> = (1..3).asFlow()

fun stringFlow(): Flow<String> = flowOf("one", "two", "three")

fun main() {
    runBlocking {
        simpleFlow().collect { println(it) }
        rangeAsFlow().collect { println(it) }
        stringFlow().collect { println(it) }
    }
}
```

flowOf vs asFlow

🧁 类比：分蛋糕 vs 蛋糕自己走上台

想象你是主持人，要请几位嘉宾上台发言：
• flowOf("one", "two", "three")：就像你一个一个喊名字让嘉宾上来（你主动提供了每个嘉宾的名字）。
•    (1..3).asFlow()：则是你说“请所有坐在1号到3号座位的嘉宾自己上来”——它是把一群本来就组织好的人（集合）变成发言的流。

## 🏗 第四步：Flow operators

Flow 提供了很多操作符来处理数据流，常用的有：

- map：转换数据
- filter：过滤数据
- collect：收集数据
- onEach：对每个元素执行操作

✨ 示例：

```kotlin	
fun main() = runBlocking {
	val flow = (1..5).asFlow()
		.map { it * 2 } // 每个元素乘以2
		.filter { it > 5 } // 只保留大于5的元素
		.onEach { println("Processing $it") } // 对每个元素打印处理信息

	flow.collect { value ->
		println("Collected: $value")
	}
}
```

## 🧪 第五步：Flow exceptions

Flow 处理异常的方式与普通代码不同，常用的有：

- catch：捕获异常并处理
- retry：重试操作
- onCompletion：在流完成时执行操作
- onEach：对每个元素执行操作，即使发生异常
  ✨ 示例：

```kotlin
fun main() = runBlocking {
    val flow = flow {
        emit(1)
        emit(2)
        throw RuntimeException("Error occurred")
        emit(3) // 这行不会被执行
    }.catch { e ->
        println("Caught exception: ${e.message}")
    }

    flow.collect { value ->
        println("Collected: $value")
    }
}
```

## 🧭 第六步：Flow cancellation
Flow 支持取消操作，常用的有：
- cancel：取消流的收集
- onCompletion：在流完成时执行操作
- onEach：对每个元素执行操作，即使流被取消
- collect：收集数据
- collectLatest：收集最新的数据，取消之前的收集

✨ 示例：

```kotlin 
fun main() = runBlocking {
    val flow = flow {
        for (i in 1..5) {
            delay(1000) // 模拟耗时操作
            emit(i)
        }
    }

    val job = launch {
        flow.collect { value ->
            println("Collected: $value")
        }
    }

    delay(2500) // 等待一段时间后取消收集
    job.cancel() // 取消收集
    println("Collection cancelled")
}
```

# ✅ StateFlow 是什么？

StateFlow = 热流（Hot）+ 持有最新状态
就像 LiveData，但更协程友好


StateFlow
✅ 永远有一个当前值
✅ 有人没人都活着
✅ 新订阅者会收到最新值

StateFlow = 某个房间恒温器的温度显示
不管你什么时候进房间，都能看到当前温度

# ✅ SharedFlow 是什么？
SharedFlow = 热流（Hot）+ 不存最新状态（默认）
❌ 默认不存最新值
❌ 新订阅者不会补发之前的值 （可配置 replay）
✅ 常用于一次性事件 - Toast、导航、弹窗

SharedFlow = 公告喇叭 🗣
听到算你幸运，错过就没了（除非 replay）


