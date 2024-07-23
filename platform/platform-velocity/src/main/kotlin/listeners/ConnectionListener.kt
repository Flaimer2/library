package ru.snapix.library.velocity.listeners

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import ru.snapix.library.network.player.statistic.Statistics
import ru.snapix.library.velocity.plugin

class ConnectionListener {
    @Subscribe(order = PostOrder.EARLY)
    fun onLogin(event: LoginEvent) {
        val username = event.player.username
        plugin.addPlayer(username)
        Statistics.update(username)
    }

    @Subscribe(order = PostOrder.EARLY)
    fun onDisconnect(event: DisconnectEvent) {
        plugin.removePlayer(event.player.username)
    }
}