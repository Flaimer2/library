package ru.snapix.library.caches

import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient

abstract class Cache<T> {
    protected abstract val KEY_REDIS: String

    operator fun get(key: String): T? {
        return redisClient.async {
            val value = hget(KEY_REDIS, key.lowercase())

            if (value != null) decode(value) else update(key)
        }
    }

    fun update(key: String): T? {
        return redisClient.async {
            val value = valueFromDatabase(key)

            if (value != null)
                hset(KEY_REDIS, key.lowercase() to encode(value))
            else
                hdel(KEY_REDIS, key.lowercase())

            value
        }
    }

    fun update(value: T) {
        redisClient.async {
            hset(KEY_REDIS, key(value) to encode(value))
        }
    }

    fun values(): List<T> {
        return redisClient.async {
            hvals(KEY_REDIS).map { decode(it) }
        }
    }

    protected abstract fun decode(value: String): T
    protected abstract fun encode(value: T): String
    protected abstract fun key(value: T): String
    protected abstract fun valueFromDatabase(key: String): T?
}
