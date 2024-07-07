package ru.snapix.library.menu

import ru.snapix.library.menu.items.Item

class PageList {
    private val map = mutableMapOf<Int, ItemMap>()

    operator fun set(page: Int, slot: Int, item: Item) {
        val itemMap = map[page]
        if (itemMap != null) {
            itemMap[slot] = item
        } else {
            map[page] = itemMapOf(slot to item)
        }
    }

    fun getItemMap(page: Int): ItemMap? {
        return map[page]
    }
}