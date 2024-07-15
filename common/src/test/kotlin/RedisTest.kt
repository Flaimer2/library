package ru.snapix.library

import io.github.crackthecodeabhi.kreds.connection.AbstractKredsSubscriber
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
    }
}
