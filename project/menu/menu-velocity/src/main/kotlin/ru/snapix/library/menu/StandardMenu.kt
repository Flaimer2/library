package ru.snapix.library.menu

import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.inventory.InventoryType
import ru.snapix.library.extensions.firstEmpty


class StandardMenu(override val settings: MenuSettings) : Menu {
    val inventory: Inventory

    init {
        inventory = Inventory(InventoryType.chestInventoryWithRows(settings.rows))
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun update(slot: Int) {
        TODO("Not yet implemented")
    }

    override fun <T> addItem(item: T) {
        setItem(inventory.firstEmpty(), item)
    }

    override fun <T> setItem(slot: Int, item: T) {
        inventory.item(slot, item as ItemStack)
    }

    override fun setTitle(title: String) {
        inventory.title(ChatElement.ofLegacyText<Any>(settings.title))
    }
}