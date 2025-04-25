package ru.snapix.library.cache

import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient

abstract class RedisCache<T> : Cache<T> {
    override operator fun get(key: String): T? {
        return redisClient.async {
            val value = hget(KEY_REDIS, key.lowercase())

            if (value != null) decode(value) else null
        }
    }

    override fun update(value: T) {
        redisClient.async {
            hset(KEY_REDIS, key(value).lowercase() to encode(value))
        }
    }

    override fun values(): List<T> {
        return redisClient.async {
            hvals(KEY_REDIS).map { decode(it) }
        }
    }

    fun add(value: T) {
        redisClient.async {
            hset(KEY_REDIS, key(value).lowercase() to encode(value))
        }
    }

    fun remove(key: String) {
        redisClient.async {
            hdel(KEY_REDIS, key.lowercase())
        }
    }

    fun remove(value: T) {
        remove(key(value).lowercase())
    }
}