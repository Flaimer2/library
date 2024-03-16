package ru.snapix.library.common.database

class DbRow : HashMap<String, Any>() {
    operator fun get(column: String, def: Any): Any {
        return get(column) ?: return def
    }
}