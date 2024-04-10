package ru.snapix.library.menu

import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.api.chat.ChatElement
import net.querz.nbt.tag.CompoundTag

interface MenuItem {
    var name: String
    var amount: Int
    var lore: List<String>
    var hideFlags: Int
    var durability: Int
    var nbtData: CompoundTag

    fun addToLore(line: String)
}

class VelocityItemStack : MenuItem {
    private val item = ItemStack.NO_DATA
    override var name: String
        get() = item.displayName().asLegacyText()
        set(value) { item.displayName(ChatElement.ofLegacyText<String>(translateAlternateColorCodes(value))) }
    override var amount: Int
        get() = item.amount()
        set(value) = item.amount(value)
    override var lore: List<String>
        get() = item.lore()
        set(value) = item.lore(value.map { ChatElement.ofLegacyText<String>(translateAlternateColorCodes(it)) })
    override var hideFlags: Int
        get() = item.hideFlags()
        set(value) = item.hideFlags(value)
    override var durability: Int
        get() = item.durability()
        set(value) = item.durability(durability)
    override var nbtData: CompoundTag
        set(value) {}

    override fun addToLore(line: String) {
        TODO("Not yet implemented")
    }
}
//class BukkitItemStack : MenuItem {
//}