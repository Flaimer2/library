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

    fun replace(player: Player, replacements: Map<String, String>, updateReplacements: (String) -> String) {
        var name = name
        if (name != null) {
            name = PlaceholderAPI.setPlaceholders(player, updateReplacements(name))
            name = replacements.replace(name)
        }

        this.name = name

        lore = replacements.replace(lore)
        lore = lore.map(updateReplacements)
    }

    public override fun clone(): Item {
        return Item(index, name, material, amount, lore, itemFlag, clickAction, head, condition)
    }
}