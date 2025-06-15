package com.example.androidflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // 模拟异步操作
        emit(i)    // 发出一个值
    }
}

fun main() {
    runBlocking {
        simple().collect { value ->
            println(value)
        }
    }
}