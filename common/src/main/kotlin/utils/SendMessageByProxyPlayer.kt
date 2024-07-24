package ru.snapix.library.utils

import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage

fun Player.message(message: String, vararg pairs: Pair<String, Any>) {
    if (message == "" || message == "null") return
    var result = message

    pairs.forEach { (key, value) -> result = result.replace("%$key%", value.toString()) }

    if (result.startsWith("<mm>", ignoreCase = true)) {
        result = result.replace("<mm>", "", ignoreCase = true)
        sendMessage(miniMessage().deserialize(result))
    } else {
        sendMessage(translateAlternateColorCodes(result).toComponent())
    }
}