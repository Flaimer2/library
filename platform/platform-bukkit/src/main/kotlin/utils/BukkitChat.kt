package ru.snapix.library.bukkit.utils

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.concurrent.ConcurrentHashMap

fun Player.nextChat(function: (message: String) -> Unit) {
    ChatListener.inputs[name] = function
}

fun Player.nextChat(function: (message: String) -> Unit, reuse: (player: Player) -> Unit = {}) {
    if (ChatListener.inputs.containsKey(name)) {
        reuse(this)
    } else {
        ChatListener.inputs[name] = function
    }
}

fun Player.cancelNextChat(execute: Boolean = true) {
    val listener = ChatListener.inputs.remove(name)
    if (listener != null && execute) {
        listener("")
    }
}

internal object ChatListener : Listener {

    val inputs = ConcurrentHashMap<String, (String) -> Unit>()

    @EventHandler
    fun e(e: PlayerQuitEvent) {
        inputs.remove(e.player.name)
    }

    @EventHandler
    fun e(e: AsyncPlayerChatEvent) {
        if (inputs.containsKey(e.player.name)) {
            inputs.remove(e.player.name)?.invoke(e.message)
            e.isCancelled = true
        }
    }
}