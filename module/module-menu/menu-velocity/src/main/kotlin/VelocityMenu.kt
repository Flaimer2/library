package ru.snapix.library.menu

import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.inventory.InventoryType

class VelocityMenu : Menu<ItemStack>() {
    override val type = MenuType.DEFAULT
    override var title: String = ""
        set(value) {
            inventory.title(title.toChatElement())
            field = value
        }
    override var rows = 5
        set(value) {
            inventory.type(InventoryType.chestInventoryWithRows(rows))
            field = value
        }
    private val inventory = Inventory(InventoryType.chestInventoryWithRows(rows))

    override fun addItem(item: ItemStack) {
        setItem(item, inventory.firstEmpty())
    }

    override fun setItem(item: ItemStack, slot: Int) {
        inventory.item(slot, item)
    }

    override fun removeItem(slot: Int) {
        inventory.removeItem(slot)
    }
}