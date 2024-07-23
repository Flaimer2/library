package ru.snapix.library.cache

import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient

abstract class DatabaseCache<T> : Cache<T> {
    override operator fun get(key: String): T? {
        return redisClient.async {
            val value = hget(KEY_REDIS, key.lowercase())

            if (value != null) decode(value) else update(key)
        }
    }

    override fun update(value: T) {
        redisClient.async {
            hset(KEY_REDIS, key(value) to encode(value))
        }
    }

    override fun values(): List<T> {
        return redisClient.async {
            hvals(KEY_REDIS).map { decode(it) }
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

    protected abstract fun valueFromDatabase(key: String): T?
}