package ru.snapix.library.utils

import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import org.bukkit.entity.Player
import ru.snapix.library.adventure

fun Player.message(message: String, vararg pairs: Pair<String, Any>) {
    var result = message

    pairs.forEach { (key, value) -> result = result.replace("%$key%", value.toString()) }

    if (result.startsWith("<mm>", ignoreCase = true)) {
        result = result.replace("<mm>", "", ignoreCase = true)
        val audience = adventure.player(player)
        audience.sendMessage(miniMessage().deserialize(result))
    } else {
        player.sendMessage(translateAlternateColorCodes(result))
    }
}