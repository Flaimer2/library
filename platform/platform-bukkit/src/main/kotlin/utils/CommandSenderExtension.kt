package ru.snapix.library.bukkit.utils

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

fun CommandSender.formattedName(consoleName: String = "console"): String {
    return if (this is ConsoleCommandSender) consoleName else name
}