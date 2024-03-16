package ru.snapix.library.database

class DbRow : HashMap<String, Any>() {
    operator fun get(column: String, def: Any): Any {
        return get(column) ?: return def
    }
}