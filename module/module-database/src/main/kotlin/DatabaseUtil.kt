package ru.snapix.library.database

import com.zaxxer.hikari.HikariConfig

inline fun <T> Array<out T>.forEachIndexed(startIndex: Int, action: (index: Int, T) -> Unit) {
    var index = startIndex
    for (item in this) action(index++, item)
}

fun HikariConfig.addDataSourceProperty(vararg pairs: Pair<String, Any>) {
    for ((key, value) in pairs) {
        addDataSourceProperty(key, value)
    }
}