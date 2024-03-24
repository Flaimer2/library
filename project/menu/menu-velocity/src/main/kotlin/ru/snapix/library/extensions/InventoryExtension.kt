package ru.snapix.library.extensions

import dev.simplix.protocolize.api.inventory.Inventory

fun Inventory.firstEmpty(): Int {
    val inventory: List<Int?> = items().keys.toList()
    for (i in 0..54) {
        if (inventory[i] == null) {
            return i
        }
    }
    return -1
}