package ru.snapix.library.network.player

import com.alessiodp.lastloginapi.api.LastLogin
import com.velocitypowered.api.proxy.ProxyServer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.Platform
import ru.snapix.library.network.player.statistic.Statistics
import kotlin.jvm.optionals.getOrNull

class OfflineNetworkPlayer(val name: String) : NetworkPlayer {
    val player = LastLogin.getApi().getPlayerByName(name)?.firstOrNull()

    override fun getName(): String {
        val name = player?.name ?: name
        return name
    }

    override fun hasPlayedBefore(): Boolean {
        return player != null
    }

    override fun isOnline(): Boolean {
        return player?.isOnline == true
    }

    override fun getStatistics(): Replacement {
        return Statistics.getReplacements(name)
    }

    override fun getPlayer(): Any? {
        return when (SnapiLibrary.platform) {
            Platform.BUKKIT -> getBukkitPlayer()
            Platform.VELOCITY -> getProxyPlayer()
            Platform.UNKNOWN -> null
        }
    }

    override fun getBukkitPlayer(): Player? {
        return Bukkit.getPlayerExact(name)
    }

    override fun getProxyPlayer(): com.velocitypowered.api.proxy.Player? {
        return (SnapiLibrary.server!! as ProxyServer).getPlayer(name).getOrNull()
    }
}