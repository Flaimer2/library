package ru.snapix.library

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import ru.snapix.library.utils.translateAlternateColorCodes

fun CommandSource?.message(message: String?, vararg pairs: Pair<String, Any>) {
    if (this == null) return
    if (message == "" || message == "null") return

    var result = message ?: return

    pairs.forEach { result = result.replace("%${it.first}%", it.second.toString(), ignoreCase = true) }

    if (this is Player && result.startsWith("<mm>", ignoreCase = true)) {
        result = result.replace("<mm>", "", ignoreCase = true)
        sendMessage(miniMessage().deserialize(result))
    } else {
        sendMessage(translateAlternateColorCodes(result).toComponent())
    }
}

fun CommandSource?.message(messages: List<String>, vararg pairs: Pair<String, Any>) {
    if (messages.isEmpty()) {
        return
    }
    messages.forEach { message(it, *pairs) }
}

fun CommandSource?.message(messages: Array<String>, vararg pairs: Pair<String, Any>) {
    message(messages.toList(), *pairs)
}