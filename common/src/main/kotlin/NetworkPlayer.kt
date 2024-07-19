package ru.snapix.library

import com.alessiodp.lastloginapi.api.LastLogin
import net.luckperms.api.LuckPermsProvider


enum class PlayerStatus {
    OFFLINE,
    ONLINE,
    NOT_FOUND
}

class NetworkPlayer(private val name: String) {
    private val player = LastLogin.getApi().getPlayerByName(name)?.firstOrNull()

    fun playerStatus(): PlayerStatus {
        if (!isExist()) {
            return PlayerStatus.NOT_FOUND
        }
        if (isOnline()) {
            return PlayerStatus.ONLINE
        }
        return PlayerStatus.OFFLINE
    }

    fun name(): String {
        return player?.name ?: name
    }

    fun isExist(): Boolean {
        return player != null
    }

    fun isOnline(): Boolean {
        return player?.isOnline == true
    }

    fun getLevel(): Int {
        return SnapiLibrary.globalDatabase.executeQuery("SELECT * FROM `account_levels` WHERE `playername` = '${name()}'") {
            it.getInt("lastlevel")
        }.firstOrNull() ?: 0
    }

    fun prefix(): String? {
        val api = LuckPermsProvider.get()
        val user = api.userManager.getUser(player?.playerUUID ?: return null) ?: return null
        return user.cachedData.metaData.prefix
    }

    fun suffix(): String? {
        val api = LuckPermsProvider.get()
        val user = api.userManager.getUser(player?.playerUUID ?: return null) ?: return null
        return user.cachedData.metaData.suffix
    }
}
