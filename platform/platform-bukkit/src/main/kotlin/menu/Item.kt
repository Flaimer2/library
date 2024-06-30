package ru.snapix.library.menu

import com.cryptomorin.xseries.XMaterial
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Item(val index: Char, var name: String? = null, var material: XMaterial = XMaterial.AIR, var amount: Int = 1, var lore: List<String> = emptyList(), var itemFlag: List<ItemFlag> = emptyList(), var clickAction: ClickAction? = null) : Cloneable {
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