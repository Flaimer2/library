package ru.snapix.library.menu

import com.cryptomorin.xseries.XMaterial
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Item(val index: Char, var name: String? = null, var material: XMaterial = XMaterial.AIR, var amount: Int = 1, var lore: MutableList<String> = mutableListOf(), var itemFlag: MutableList<ItemFlag> = mutableListOf(), var clickAction: (ClickAction.() -> Unit)? = null) : Cloneable {
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

    fun addLore(vararg lore: String) {
        this.lore.addAll(lore)
    }

    fun addItemFlag(vararg itemFlag: ItemFlag) {
        this.itemFlag.addAll(itemFlag)
    }

    public override fun clone(): Item {
        return Item(index, name, material, amount, lore, itemFlag, clickAction)
    }
}