package ru.snapix.library.network.player

import com.velocitypowered.api.proxy.ProxyServer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.Platform
import ru.snapix.library.network.player.statistic.Statistics
import kotlin.jvm.optionals.getOrNull

@Serializable
@SerialName("online_network_player")
class OnlineNetworkPlayer(private val name: String) : NetworkPlayer {
    override fun getName(): String {
        return name
    }

    override fun hasPlayedBefore(): Boolean {
        return true
    }

    override fun isOnline(): Boolean {
        return true
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
