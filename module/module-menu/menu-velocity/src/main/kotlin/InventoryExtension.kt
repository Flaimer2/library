package ru.snapix.library.menu

import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.data.inventory.InventoryType
import ru.snapix.library.utils.translateAlternateColorCodes

fun Inventory.firstEmpty(): Int {
    val inventory: List<Int?> = items().keys.toList()
    if (inventory.isEmpty()) return -1

    for (i in 0..type().getSize()) {
        if (inventory[i] == null) return i
    }
    return -1
}

fun String.toChatElement(): ChatElement<String> {
    return ChatElement.ofLegacyText(translateAlternateColorCodes(this))
}

fun InventoryType.getSize(): Int {
    return getTypicalSize(47)
}