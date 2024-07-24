package ru.snapix.library.network.messenger

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.snapix.library.network.player.NetworkPlayer

@Serializable
@SerialName("sendmessage")
class SendMessageAction(val networkPlayer: NetworkPlayer, val message: String) : Action() {
    override fun executeIncomingMessage() {
        networkPlayer.sendMessage(message)
    }
}