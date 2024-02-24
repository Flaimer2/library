package ru.snapix.library.common.utils

import com.zaxxer.hikari.HikariConfig

fun HikariConfig.addDataSourceProperty(vararg pairs: Pair<String, Any>) {
    for ((key, value) in pairs) {
        addDataSourceProperty(key, value)
    }
}