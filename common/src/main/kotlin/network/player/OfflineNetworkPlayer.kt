package ru.snapix.library.network.player

import com.alessiodp.lastloginapi.api.LastLogin
import com.velocitypowered.api.proxy.ProxyServer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.Platform
import ru.snapix.library.network.player.statistic.Statistics
import kotlin.jvm.optionals.getOrNull

@Serializable
@SerialName("offline_network_player")
class OfflineNetworkPlayer(private val name: String) : NetworkPlayer {
    @Transient private val player = LastLogin.getApi().getPlayerByName(name)?.firstOrNull()

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

    override fun equals(other: Any?): Boolean {
        if (other !is NetworkPlayer) return  false
        return other.getName().equals(getName(), ignoreCase = true)
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}