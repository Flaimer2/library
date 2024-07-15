package ru.snapix.library.caches

interface RedisCache<T> {
    val KEY_REDIS: String
    operator fun get(key: String): T?
    fun add(key: String, value: T)
    fun remove(key: String, value: T)
    fun update(key: String, value: T)
    fun values(): List<T>
}