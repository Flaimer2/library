package ru.snapix.library

import kotlinx.serialization.Serializable

@Serializable
class Replacement : MutableMap<String, () -> Any> by mutableMapOf() {
    fun replace(text: String): String {
        var result = text
        val map = mapValues { it.value().toString() }
        for ((key, value) in map) {
            result = result.replace(key, value, ignoreCase = true)
        }
        return result
    }

    fun replace(text: List<String>): List<String> {
        val result = mutableListOf<String>()
        val map = mapValues { it.value().toString() }

        for (tx in text) {
            var rs = tx
            for ((key, value) in map) {
                rs = rs.replace(key, value, ignoreCase = true)
            }
            result.add(rs)
        }

        return result
    }
}

fun Map<String, String>.replace(text: String): String {
    var result = text
    for ((key, value) in this) {
        result = result.replace(key, value, ignoreCase = true)
    }
    return result
}

fun Map<String, String>.replace(text: List<String>): List<String> {
    val result = mutableListOf<String>()

    for (tx in text) {
        var rs = tx
        for ((key, value) in this) {
            rs = rs.replace("{$key}", value, ignoreCase = true)
        }
        result.add(rs)
    }

    return result
}