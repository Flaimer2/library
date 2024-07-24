package ru.snapix.library.network.messenger

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.snapix.library.network.player.NetworkPlayer

@Serializable
@SerialName("sendmessage")
class SendMessageAction(val players: List<NetworkPlayer>, val message: String, vararg val pair: Pair<String, String>) : Action() {
    override fun executeIncomingMessage() {
        players.forEach { it.sendMessage(message, *pair) }
    }
}