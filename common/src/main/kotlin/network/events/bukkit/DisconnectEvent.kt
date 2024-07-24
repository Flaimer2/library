package ru.snapix.library.network.events.bukkit

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import ru.snapix.library.network.player.NetworkPlayer

class DisconnectEvent(val player: NetworkPlayer) : Event() {
    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

    companion object {
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }
}