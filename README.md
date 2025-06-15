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

🌪 第二步：Flow vs suspend function

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
🧩 第三步：Flow builder

官方列举了几种 Flow 构建方式，常用的有：
flow {}: 手动发射值，常见于复杂异步操作
flowOf(): 直接创建固定值流
asFlow(): 把集合转成 flow

✨ 示例：
```kotlin
val numbers = (1..3).asFlow()
numbers.collect { println(it) }
```




