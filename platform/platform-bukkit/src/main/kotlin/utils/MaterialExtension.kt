package ru.snapix.library.bukkit.utils

import org.bukkit.Material
import org.bukkit.Material.*

fun Material.isBreakable(): Boolean {
    return when (this) {
        BARRIER, BEDROCK, COMMAND, COMMAND_CHAIN,
        COMMAND_REPEATING, END_GATEWAY,
        ENDER_PORTAL, ENDER_PORTAL_FRAME, PORTAL,
        STRUCTURE_BLOCK -> false
        else -> true
    }
}