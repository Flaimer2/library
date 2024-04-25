package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.ClickType
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.api.item.BaseItemStack

val InventoryClick.clickedItem: BaseItemStack
    get() = clickedItem()
val InventoryClick.player: Player
    get() = player().handle()
val InventoryClick.inventory: Inventory
    get() = inventory()
val InventoryClick.clickType: ClickType
    get() = clickType()
val InventoryClick.slot: Int
    get() = slot()
fun InventoryClick.close() {
    player().closeInventory()
}