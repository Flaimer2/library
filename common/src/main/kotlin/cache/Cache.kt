package ru.snapix.library.cache

interface Cache<T> {
    val KEY_REDIS: String
    operator fun get(key: String): T?
    fun update(value: T)
    fun values(): List<T>

    fun decode(value: String): T
    fun encode(value: T): String
    fun key(value: T): String
}