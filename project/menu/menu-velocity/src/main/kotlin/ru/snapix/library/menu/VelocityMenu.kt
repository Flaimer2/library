package ru.snapix.library.menu

import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.inventory.InventoryType
import ru.snapix.library.extensions.firstEmpty

class VelocityMenu : Menu() {
    override val type: MenuType = MenuType.DEFAULT
    val inventory: Inventory = Inventory(InventoryType.chestInventoryWithRows(rows))
    override var title: String = "Empty"
    override var rows: Int = 5
//    override var updateDelay: Long

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun update(slot: Int) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: MenuItem) {
        setItem(item, inventory.firstEmpty())
    }

    override fun setItem(item: MenuItem, slot: Int) {
        inventory.item(slot, item as ItemStack)
    }

    override fun setTitle(title: String) {
        this.title = title
        inventory.title(ChatElement.ofLegacyText<String>(title))
    }

    override fun setRows(rows: Int) {
        this.rows = rows
        inventory.type(InventoryType.chestInventoryWithRows(rows))
    }
}