package ru.snapix.library

import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient

fun players(): List<String> {
    return redisClient.async {
        smembers("proxy-player")
    }
}