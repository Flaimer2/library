package ru.snapix.library.extensions

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import ru.snapix.library.menu.Menu
import ru.snapix.library.menu.StandardMenu

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
    if (menu is StandardMenu) {
        Protocolize.playerProvider().player(uniqueId).openInventory(menu.inventory)
        println("Try open menu " + menu.inventory)
        return
    }
    error("Don't know how to open $menu")
}