package ru.snapix.library.menu

import dev.simplix.protocolize.api.item.ItemFlag
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType

typealias Material = ItemType

class Item(val index: Char, var name: String? = null, var material: Material = Material.AIR, var amount: Int = 1, var lore: List<String> = emptyList(), var itemFlag: List<ItemFlag> = emptyList(), var clickAction: ClickAction? = null) : Cloneable {
    fun item(): ItemStack {
        val item = ItemStack(material, amount)

        if (name != null) {
            item.displayName(name!!.toChatElement())
        }

        item.lore(lore.map { it.toChatElement() })

        itemFlag.forEach {
            item.itemFlag(it, true)
        }

        return item
    }

    public override fun clone(): Item {
        return Item(index, name, material, amount, lore, itemFlag, clickAction)
    }
}