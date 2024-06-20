package ru.snapix.library

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun CommandSender?.message(message: String?, vararg pairs: Pair<String, Any>) {
    if (this == null) return
    if (message == "" || message == "null") return
    var result = message ?: return
    pairs.forEach { result = result.replace("%${it.first}%", it.second.toString(), ignoreCase = true) }
    sendMessage(ChatColor.translateAlternateColorCodes('&', result))
}

fun CommandSender?.message(messages: List<String>, vararg pairs: Pair<String, Any>) {
    if (messages.isEmpty()) {
        return
    }
    messages.forEach { message(it, *pairs) }
}

fun CommandSender?.message(messages: Array<String>, vararg pairs: Pair<String, Any>) {
    message(messages.toList(), *pairs)
}
