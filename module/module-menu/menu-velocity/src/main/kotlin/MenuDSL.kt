package ru.snapix.library.menu

import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType

fun menu(menuType: MenuType = MenuType.DEFAULT, block: VelocityMenu.() -> Unit): VelocityMenu =
    MenuFactory.createMenu(menuType).apply(block)

fun VelocityMenu.items(block: Items.() -> Unit) = Items(this).apply(block)

@Suppress("MemberVisibilityCanBePrivate")
class Items(val menu: VelocityMenu) {
    operator fun String.invoke(block: Item.() -> Unit) = Item(menu).apply {
        block()
        add()
    }

    class Item(val menu: VelocityMenu) {
        var material: ItemType = ItemType.BEDROCK
        var name: String? = null
        var lore: List<String> = emptyList()
        var slots: List<Int> = emptyList()
        var slot = -1

        fun add() {
            val item = ItemStack(material)
            if (name != null) {
                item.displayName(name!!.toChatElement())
            }
            if (lore.isNotEmpty()) {
                item.lore(lore.map { it.toChatElement() })
            }
            if (slots.isNotEmpty()) {
                slots.forEach { menu.setItem(item, it) }
                return
            }
            if (slot == -1) {
                menu.addItem(item)
            } else {
                menu.setItem(item, slot)
            }
        }

        fun actions(block: InventoryClick.() -> Unit) {
            if (slots.isNotEmpty()) {
                slots.forEach { menu.addClickAction(it, block) }
                return
            }
            if (slot != -1) {
                menu.addClickAction(slot, block)
            }
        }
    }
}
