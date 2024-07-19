package ru.snapix.library.caches

import ru.snapix.library.redis.async
import ru.snapix.library.redis.redisClient

abstract class RedisCache<T> {
    protected abstract val KEY_REDIS: String

    operator fun get(key: String): T? {
        return redisClient.async {
            val value = hget(KEY_REDIS, key.lowercase())

            if (value != null) decode(value) else null
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
        redisClient.async {
            hdel(KEY_REDIS, key(value).lowercase())
        }
    }

    fun update(value: T) {
        redisClient.async {
            hset(KEY_REDIS, key(value).lowercase() to encode(value))
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
}