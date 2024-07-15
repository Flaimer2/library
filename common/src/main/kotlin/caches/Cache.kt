package ru.snapix.library.caches

interface Cache<T> {
    val KEY_REDIS: String
    operator fun get(key: String): T?
    fun update(key: String): T?
    fun update(value: T)
    fun values(): List<T>
}
