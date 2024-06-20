package ru.snapix.library

import io.github.crackthecodeabhi.kreds.connection.AbstractKredsSubscriber
import org.junit.jupiter.api.Test
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient
import ru.snapix.library.redis.subscribe

class RedisTest {
    @Test
    fun `Connection Test`() {
        redisClient.async {
            hset("test2", "hello" to "s")
        }
        subscribe(object : AbstractKredsSubscriber() {
            override fun onException(ex: Throwable) {
                ex.printStackTrace()
            }

            override fun onMessage(channel: String, message: String) {
                print("$channel: $message")
            }

            override fun onSubscribe(channel: String, subscribedChannels: Long) {
                println("sub $channel")
            }

            override fun onUnsubscribe(channel: String, subscribedChannels: Long) {
                println("unsub $channel")
            }

        }, "test")
        while (true) {
            continue
        }
    }
}