package ru.snapix.library.menu

import ru.snapix.library.menu.items.Item

typealias ItemMap = MutableMap<Int, Item>

fun itemMapOf(vararg pairs: Pair<Int, Item>): ItemMap {
    return mutableMapOf(*pairs)
}

fun emptyItemMap(): ItemMap {
    return itemMapOf()
}
