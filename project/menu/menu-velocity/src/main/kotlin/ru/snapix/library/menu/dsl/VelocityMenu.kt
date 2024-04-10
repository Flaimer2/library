package ru.snapix.library.menu.dsl

import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType
import ru.snapix.library.menu.Menu
import ru.snapix.library.menu.MenuFactory
import ru.snapix.library.menu.MenuType
import ru.snapix.library.menu.VelocityMenu

fun menu(menuType: MenuType = MenuType.DEFAULT, block: Menu.() -> Unit): Menu = MenuFactory.createMenu(menuType).apply(block)
fun Menu.items(block: Items.() -> Unit) = Items(this).apply(block)

class Items(val menu: Menu) {
    operator fun String.invoke(block: Item.() -> Unit) = Item(menu).apply {
        block()
        add()
    }

    class Item(val menu: Menu) {
        var material: ItemType = ItemType.BEDROCK
        var name: String? = null
        var lore = emptyList<String>()
        var slot: Int = -1

        fun add() {
            val item = ItemStack(material)
            if (name != null) {
                item.displayName(ChatElement.ofLegacyText<String>(name))
            }
            if (lore.isNotEmpty()) {
                item.lore(lore.map { ChatElement.ofLegacyText<String>(it) })
            }
            if (slot == -1) {
                menu.addItem(item)
            } else {
                menu.setItem(slot, item)
            }
        }
    }
}
