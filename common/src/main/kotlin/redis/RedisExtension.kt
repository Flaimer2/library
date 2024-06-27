package ru.snapix.library.redis

import io.github.crackthecodeabhi.kreds.args.SetOption
import io.github.crackthecodeabhi.kreds.connection.*
import kotlinx.coroutines.*

val redisClient = newClient(Endpoint.from("127.0.0.1:6379"))

fun subscribe(handler: KredsSubscriber, channel: String) {
    CoroutineScope(Dispatchers.Default).launch {
        newSubscriberClient(Endpoint.from("127.0.0.1:6379"), handler).subscribe(channel)
    }
}

fun <V> KredsClient.async(block: suspend KredsClient.() -> V): V = runBlocking {
    async { block() }.await()
}

fun setOption(builder: SetOption.Builder.() -> Unit): SetOption {
    return SetOption.Builder().apply(builder).build()
}
