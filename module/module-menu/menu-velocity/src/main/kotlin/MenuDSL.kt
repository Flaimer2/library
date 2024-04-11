package ru.snapix.library.menu

import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType

fun menu(menuType: MenuType = MenuType.DEFAULT, block: Menu<ItemStack>.() -> Unit): Menu<ItemStack> =
    MenuFactory.createMenu(menuType).apply(block)

fun Menu<ItemStack>.items(block: Items.() -> Unit) = Items(this).apply(block)


@Suppress("MemberVisibilityCanBePrivate")
class Items(val menu: Menu<ItemStack>) {
    operator fun String.invoke(block: Item.() -> Unit) = Item(menu).apply {
        block()
        add()
    }

    class Item(val menu: Menu<ItemStack>) {
        var material: ItemType = ItemType.BEDROCK
        var name: String? = null
        var lore = emptyList<String>()
        var slot: Int = -1

        fun add() {
            val item = ItemStack(material)
            if (name != null) {
                item.displayName(name!!.toChatElement())
            }
            if (lore.isNotEmpty()) {
                item.lore(lore.map { it.toChatElement() })
            }
            if (slot == -1) {
                menu.addItem(item)
            } else {
                menu.setItem(item, slot)
            }
        }
    }
}
