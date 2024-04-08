package ru.snapix.library.menu

import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.inventory.InventoryType
import ru.snapix.library.extensions.firstEmpty


class StandardMenu(override val settings: MenuSettings) : Menu {
    val inventory: Inventory = Inventory(InventoryType.chestInventoryWithRows(settings.rows))
    override var title: String = ""
        set(value) {
            inventory.title(ChatElement.ofLegacyText<String>(value))
        }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun update(slot: Int) {
        TODO("Not yet implemented")
    }

    fun addItem(item: ItemStack) {
        setItem(inventory.firstEmpty(), item)
    }

    fun setItem(slot: Int, item: ItemStack) {
        inventory.item(slot, item)
    }
}