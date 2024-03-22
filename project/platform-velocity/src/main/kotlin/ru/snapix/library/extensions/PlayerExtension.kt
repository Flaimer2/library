package ru.snapix.library.extensions

import com.velocitypowered.api.proxy.Player
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider

private val luckPerms: LuckPerms = LuckPermsProvider.get()

fun Player.getPrefix(): String? {
    val user = luckPerms.userManager.getUser(username)
    return user?.cachedData?.metaData?.prefix
}

fun Player.getSuffix(): String? {
    val user = luckPerms.userManager.getUser(username)
    return user?.cachedData?.metaData?.suffix
}