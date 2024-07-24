package ru.snapix.library.bukkit.utils

import ru.snapix.library.network.messenger.Messenger
import ru.snapix.library.network.messenger.SendMessageAction
import ru.snapix.library.network.player.NetworkPlayer

fun List<NetworkPlayer>.sendMessage(message: String, vararg pairs: Pair<String, Any>) {
    Messenger.sendOutgoingMessage(SendMessageAction(this, message, *pairs.map { it.first to it.second.toString() }.toTypedArray()))
}