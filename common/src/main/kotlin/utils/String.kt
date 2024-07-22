package ru.snapix.library.utils

fun String.replaceLast(oldValue: String, newValue: String): String {
    return replaceFirst("(?s)$oldValue(?!.*?$oldValue)".toRegex(), newValue)
}
