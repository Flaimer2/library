package ru.snapix.library.extensions

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.Bukkit
import org.bukkit.entity.Player

private val luckPerms: LuckPerms = LuckPermsProvider.get()

fun Player.getPrefix(): String? {
    val user = luckPerms.userManager.getUser(name)
    this.inventory.addItem()
    return user?.cachedData?.metaData?.prefix
}

fun Player.getSuffix(): String? {
    val user = luckPerms.userManager.getUser(name)
    return user?.cachedData?.metaData?.suffix
}