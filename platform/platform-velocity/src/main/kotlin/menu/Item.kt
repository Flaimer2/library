package ru.snapix.library.menu

import dev.simplix.protocolize.api.item.ItemFlag
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType
import ru.snapix.library.utils.requestHead

class Item(
    val index: Char? = null,
    var name: String? = null,
    val material: ItemType = ItemType.AIR,
    val amount: Int = 1,
    var lore: List<String> = emptyList(),
    val itemFlag: List<ItemFlag> = emptyList(),
    val clickAction: ClickAction? = null,
    val head: String? = null,
    val condition: Condition = { true }
) : Cloneable {
    fun item(): ItemStack {
        val item = if (head != null) requestHead(head) else ItemStack(material, amount)

        if (name != null) {
            item.displayName(name!!.toChatElement())
        }
        val lore = buildList {
            for (lore in lore) {
                lore.split("\n").forEach { add(it) }
            }
        }

        item.lore(lore.map { it.toChatElement() })

        itemFlag.forEach {
            item.itemFlag(it, true)
        }

        return item
    }

    fun replace(replacements: List<Replacement>) {
        for (replace in replacements) {
            name = name?.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true)
            lore = lore.map {
                it.replace(
                    "{${replace.first}}",
                    replace.second().toString(),
                    ignoreCase = true
                )
            }
        }
    }

    public override fun clone(): Item {
        return Item(index, name, material, amount, lore, itemFlag, clickAction, head, condition)
    }
}