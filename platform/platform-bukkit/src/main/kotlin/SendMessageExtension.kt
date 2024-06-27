package ru.snapix.library

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

val minimessage = MiniMessage.miniMessage()

fun CommandSender?.message(message: String?, vararg pairs: Pair<String, Any>) {
    if (this == null) return
    if (message == "" || message == "null") return

    var result = message ?: return

    pairs.forEach { result = result.replace("%${it.first}%", it.second.toString(), ignoreCase = true) }

    if (this is Player && result.startsWith("<mm>", ignoreCase = true)) {
        result = result.replace("<mm>", "", ignoreCase = true)
        val audience = adventure.player(this)
        audience.sendMessage(minimessage.deserialize(result))
    } else {
        sendMessage(ChatColor.translateAlternateColorCodes('&', result))
    }
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
