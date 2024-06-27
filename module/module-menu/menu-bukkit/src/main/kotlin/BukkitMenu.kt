package ru.snapix.library.menu

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class BukkitMenu(override val type: MenuType, override var title: String, override var rows: Int) : Menu<ItemStack>(), InventoryHolder {
    override fun addItem(item: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun setItem(item: ItemStack, slot: Int) {
        TODO("Not yet implemented")
    }

    override fun removeItem(slot: Int) {
        TODO("Not yet implemented")
    }

    override fun getInventory(): Inventory {
        TODO("Not yet implemented")
    }
}