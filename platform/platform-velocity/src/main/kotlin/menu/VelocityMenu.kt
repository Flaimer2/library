package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.inventory.InventoryType

// TODO: Need to rework
class VelocityMenu {
    var title: String = ""
        set(value) {
            inventory.title(value.toChatElement())
            field = value
        }
    var rows: Int = 5
        set(value) {
            inventory.type(InventoryType.chestInventoryWithRows(field))
            field = value
        }
    private val inventory = Inventory(InventoryType.chestInventoryWithRows(rows))

    fun addItem(item: ItemStack) {
        setItem(item, inventory.firstEmpty())
    }

    fun setItem(item: ItemStack, slot: Int) {
        inventory.item(slot, item)
    }

    fun removeItem(slot: Int) {
        inventory.removeItem(slot)
    }

    fun addClickAction(slot: Int, vararg action: InventoryClick.() -> Unit) {
        if (action.isEmpty()) return
        inventory.onClick { click ->
            if (slot == click.slot()) {
                action.forEach {
                    it.invoke(click)
                }
            }
        }
    }

    fun open(player: Player) {
        Protocolize.playerProvider().player(player.uniqueId).openInventory(inventory)
    }
}