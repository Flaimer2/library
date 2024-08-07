package ru.snapix.library.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.snapix.library.KEY_REDIS_PLAYER
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.messenger.Messenger
import ru.snapix.library.network.player.NetworkPlayer
import ru.snapix.library.network.player.statistic.Statistics
import ru.snapix.library.utils.Skins
import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient
import ru.snapix.library.velocity.listeners.ConnectionListener
import ru.snapix.library.velocity.utils.repeatTask
import java.nio.file.Path
import kotlin.time.Duration.Companion.seconds

@Plugin(
    id = "snapilibrary",
    name = "SnapiLibrary",
    version = "2.0.0",
    authors = ["Flaimer"],
    dependencies = [Dependency(id = "lastloginapi", optional = true)]
)
class SnapiLibraryVelocity @Inject constructor(
    server: ProxyServer,
    logger: Logger,
    @DataDirectory dataDirectory: Path
) : VelocityPlugin() {
    init {
        instance = this
        init(server, logger, dataDirectory)
        SnapiLibrary.initVelocity(server)
    }

    @Subscribe
    fun onEnable(event: ProxyInitializeEvent) {
        plugin.server.eventManager.register(this, ConnectionListener())
        Messenger.enable()
        plugin.server.repeatTask(120.seconds) {
            plugin.server.allPlayers.forEach {
                Statistics.update(it.username)
            }
        }
        Skins.update()
    }

    @Subscribe
    fun onDisable(event: ProxyShutdownEvent) {
        getPlayers().forEach { removePlayer(it) }
    }

    fun addPlayer(name: String) {
        redisClient.async {
            sadd(KEY_REDIS_PLAYER, name)
        }
    }

    fun removePlayer(name: String) {
        redisClient.async {
            srem(KEY_REDIS_PLAYER, name)
        }
    }

    fun getPlayers(): List<String> {
        return redisClient.async {
            smembers(KEY_REDIS_PLAYER)
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: SnapiLibraryVelocity
            private set
    }
}

val plugin = SnapiLibraryVelocity.instance