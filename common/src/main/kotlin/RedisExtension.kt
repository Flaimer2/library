package ru.snapix.library

import kotlinx.coroutines.*
import redis.clients.jedis.Jedis

fun Jedis.useAsync(dispatcher: CoroutineDispatcher = Dispatchers.Main, block: Jedis.() -> Unit) {
    CoroutineScope(dispatcher).async { block() }
}

fun <V> Jedis.async(block: Jedis.() -> V): V = runBlocking {
    async { block() }.await()
}
