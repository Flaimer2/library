package ru.snapix.library

import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.platform.AudienceProvider
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.Plugin
import org.jetbrains.exposed.sql.Database
import ru.snapix.library.network.Platform
import ru.snapix.library.network.player.NetworkPlayer
import ru.snapix.library.network.player.OfflineNetworkPlayer
import ru.snapix.library.network.player.OnlineNetworkPlayer
import ru.snapix.library.utils.async
import ru.snapix.library.utils.redisClient

object SnapiLibrary {
    private var adventure: AudienceProvider? = null
    val globalDatabase = Database.connect(
        url = "jdbc:mariadb://127.0.0.1:3306/server_global",
        driver = "org.mariadb.jdbc.Driver",
        user = "root",
        password = "root"
    )
    var platform = Platform.UNKNOWN
    var server: Any? = null

    fun initBukkit(plugin: Plugin) {
        platform = Platform.BUKKIT
        server = plugin.server
        adventure = BukkitAudiences.create(plugin)
    }

    fun initVelocity(server: ProxyServer) {
        platform = Platform.VELOCITY
        this.server = server
    }

    fun disableBukkit() {
        adventure?.let {
            it.close()
            adventure = null
        }
    }

    fun adventure(): BukkitAudiences {
        checkNotNull(adventure) { "Tried to access Adventure when the plugin was disabled!" }
        return adventure as BukkitAudiences
    }

    fun getPlayer(name: String): NetworkPlayer {
        return redisClient.async {
            val value = smembers(KEY_REDIS_PLAYER).firstOrNull { it.equals(name, ignoreCase = true) }
            if (value != null) {
                OnlineNetworkPlayer(value)
            } else {
                OfflineNetworkPlayer(name)
            }
        }
    }

    fun getOnlinePlayers(): List<NetworkPlayer> {
        return redisClient.async {
            smembers(KEY_REDIS_PLAYER).map { OnlineNetworkPlayer(it) }
        }
    }
}

val adventure get() = SnapiLibrary.adventure()
const val KEY_REDIS_PLAYER = "proxy-player"