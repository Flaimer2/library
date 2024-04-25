package ru.snapix.library.menu

import org.bukkit.inventory.ItemStack

class BukkitMenu : Menu<ItemStack>() {
    override val type: MenuType
        get() = TODO("Not yet implemented")
    override var title: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var rows: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun removeItem(slot: Int) {
        TODO("Not yet implemented")
    }

    override fun setItem(item: ItemStack, slot: Int) {
        TODO("Not yet implemented")
    }

    override fun addItem(item: ItemStack) {
        TODO("Not yet implemented")
    }

}