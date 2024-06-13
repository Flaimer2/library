package ru.snapix.library.database

class DbRow : HashMap<String, Any>() {
    operator fun get(column: String, def: Any? = null): Any? {
        return get(column) ?: return def
    }

    fun getString(column: String, def: String? = null): String? {
        return get(column)?.toString() ?: return def
    }

    fun getInt(column: String, def: Int = 0): Int {
        return getString(column)?.toInt() ?: def
    }

    fun getLong(column: String, def: Long = 0L): Long {
        return getString(column)?.toLong() ?: def
    }
}