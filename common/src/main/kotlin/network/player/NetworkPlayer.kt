package ru.snapix.library.network.player

import kotlinx.serialization.Serializable
import net.kyori.adventure.text.minimessage.MiniMessage.miniMessage
import net.luckperms.api.LuckPermsProvider
import org.bukkit.entity.Player
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.adventure
import ru.snapix.library.network.Platform
import ru.snapix.library.network.messenger.Messenger
import ru.snapix.library.network.messenger.SendMessageAction
import ru.snapix.library.utils.message
import ru.snapix.library.utils.toComponent
import ru.snapix.library.utils.translateAlternateColorCodes

@Serializable
sealed interface NetworkPlayer {
    fun getName(): String
    fun sendMessage(message: String, vararg pairs: Pair<String, Any>) {
        if (SnapiLibrary.platform == Platform.UNKNOWN) return

        if (SnapiLibrary.platform == Platform.BUKKIT) {
            if (!isOnline()) return
            val player = getBukkitPlayer()
            if (player == null) {
                Messenger.sendOutgoingMessage(SendMessageAction(listOf(this), message, *pairs.map { it.first to it.second.toString() }.toTypedArray()))
                return
            }
            player.message(message, *pairs)
        }
        if (SnapiLibrary.platform == Platform.VELOCITY) {
            val player = getProxyPlayer() ?: return
            player.message(message, *pairs)
        }
    }

    fun hasPlayedBefore(): Boolean
    fun isOnline(): Boolean
    fun getStatistics(): Replacement
    fun getPlayer(): Any?
    fun getBukkitPlayer(): Player?
    fun getProxyPlayer(): com.velocitypowered.api.proxy.Player?

    fun getPrefix(): String? {
        val api = LuckPermsProvider.get()
        val user = api.userManager.getUser(getName()) ?: return null
        return user.cachedData.metaData.prefix
    }

    fun getSuffix(): String? {
        val api = LuckPermsProvider.get()
        val user = api.userManager.getUser(getName()) ?: return null
        return user.cachedData.metaData.suffix
    }
}