package ru.snapix.library.network.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import ru.snapix.library.network.events.bukkit.DisconnectEvent
import ru.snapix.library.network.messenger.Action
import ru.snapix.library.network.player.NetworkPlayer

@Serializable
@SerialName("playerdisconnect")
class PlayerDisconnectEvent(val player: NetworkPlayer) : Action() {
    override fun executeIncomingMessage() {
        Bukkit.getPluginManager().callEvent(DisconnectEvent(player))
    }
}