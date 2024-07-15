package ru.snapix.library.menu

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import ru.snapix.library.menu.dsl.Material
import ru.snapix.library.utils.requestHead

class Item(
    val index: Char? = null,
    var name: String? = null,
    val material: Material = Material.AIR,
    val amount: Int = 1,
    var lore: List<String> = emptyList(),
    val itemFlag: List<ItemFlag> = emptyList(),
    val clickAction: ClickAction? = null,
    val head: String? = null,
    val condition: Condition = { true }
) : Cloneable {
    fun item(): ItemStack {
        val item = if (head != null) requestHead(head) else material.parseItem()!!

        item.amount = amount

        val meta: ItemMeta = item.itemMeta ?: return item

        if (name != null) {
            meta.displayName = name
        }

        meta.lore = lore

        itemFlag.forEach {
            meta.addItemFlags(it)
        }

        item.itemMeta = meta

        return item
    }

    fun replace(player: Player, replacements: List<Replacement>) {
        for (replace in replacements) {
            name = name?.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true)
            lore = lore.map { it.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true) }
        }
        name?.let {
            name = PlaceholderAPI.setPlaceholders(player, it)
        }

        lore = lore.map { PlaceholderAPI.setPlaceholders(player, it) }
    }

    public override fun clone(): Item {
        return Item(index, name, material, amount, lore, itemFlag, clickAction, head, condition)
    }
}