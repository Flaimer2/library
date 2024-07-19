package ru.snapix.library.caches

abstract class LocalCache<T> {
    val map = mutableMapOf<String, T>()

    operator fun get(key: String): T? {
        return map[key.lowercase()]
    }

    fun add(value: T) {
        map[key(value).lowercase()] = value
    }

    fun remove(key: String) {
        map.remove(key.lowercase())
    }

    fun remove(value: T) {
        map.remove(key(value).lowercase())
    }

    fun update(value: T) {
        map[key(value).lowercase()] = value
    }

    fun values(): List<T> {
        return map.values.toList()
    }

    protected abstract fun key(value: T): String
}