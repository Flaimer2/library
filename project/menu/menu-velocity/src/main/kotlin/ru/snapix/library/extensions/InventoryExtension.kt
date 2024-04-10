package ru.snapix.library.extensions

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.ItemStack
import ru.snapix.library.menu.Menu
import ru.snapix.library.menu.VelocityMenu

fun Inventory.firstEmpty(): Int {
    val inventory: List<Int?> = items().keys.toList()

    if (inventory.isEmpty()) return 0

    for (i in 0..54) {
        if (inventory[i] == null) {
            return i
        }
    }
    return -1
}

fun Player.openMenu(menu: Menu) {
    if (menu is VelocityMenu) {
        Protocolize.playerProvider().player(uniqueId).openInventory(menu.inventory)
        return
    }
    error("Don't know how to open $menu")
}