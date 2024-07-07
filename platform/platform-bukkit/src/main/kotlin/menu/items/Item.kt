package ru.snapix.library.menu.items

import com.cryptomorin.xseries.XMaterial
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import ru.snapix.library.menu.ClickAction

class Item(val index: Char? = null, var name: String? = null, val material: XMaterial = XMaterial.AIR, val amount: Int = 1, var lore: List<String> = emptyList(), val itemFlag: List<ItemFlag> = emptyList(), val clickAction: ClickAction? = null) : Cloneable {
    fun item(): ItemStack {
        val item = ItemStack(material.parseMaterial(), amount)
        val meta = item.itemMeta

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

    public override fun clone(): Item {
        return Item(index, name, material, amount, lore, itemFlag, clickAction)
    }
}