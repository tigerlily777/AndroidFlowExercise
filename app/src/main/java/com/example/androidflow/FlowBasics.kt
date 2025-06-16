package com.example.androidflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

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
