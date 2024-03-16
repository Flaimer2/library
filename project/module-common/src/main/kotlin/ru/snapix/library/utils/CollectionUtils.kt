package ru.snapix.library.utils

inline fun <T> Array<out T>.forEachIndexed(startIndex: Int, action: (index: Int, T) -> Unit) {
    var index = startIndex
    for (item in this) action(index++, item)
}