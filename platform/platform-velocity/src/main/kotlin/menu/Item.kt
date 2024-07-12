package ru.snapix.library.menu

import dev.simplix.protocolize.api.item.ItemFlag
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType

class Item(val index: Char? = null, var name: String? = null, val material: ItemType = ItemType.AIR, val amount: Int = 1, var lore: List<String> = emptyList(), val itemFlag: List<ItemFlag> = emptyList(), val clickAction: ClickAction? = null) : Cloneable {
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