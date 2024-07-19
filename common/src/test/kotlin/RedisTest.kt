package ru.snapix.library

import org.junit.jupiter.api.Test
import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient

class RedisTest {
    @Test
    fun `Connection Test`() {
        redisClient.async {
            hset("test2", "hello" to "s")
        }
    }
}
