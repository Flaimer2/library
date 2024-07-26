package ru.snapix.library.utils

import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.snapix.library.adventure

fun CommandSender.message(message: String, vararg pairs: Pair<String, Any>) {
    if (message == "" || message == "null") return
    var result = message

    pairs.forEach { (key, value) -> result = result.replace("%$key%", value.toString()) }

    if (this is Player && result.startsWith("<mm>", ignoreCase = true)) {
        result = result.replace("<mm>", "", ignoreCase = true)
        val audience = adventure.player(this)
        audience.sendMessage(miniMessage().deserialize(result))
    } else {
        sendMessage(translateAlternateColorCodes(result))
    }
}