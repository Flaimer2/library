package ru.snapix.library.menu

typealias ItemMap = MutableMap<Int, Item>

fun itemMapOf(vararg pairs: Pair<Int, Item>): ItemMap {
    return mutableMapOf(*pairs)
}

fun emptyItemMap(): ItemMap {
    return itemMapOf()
}