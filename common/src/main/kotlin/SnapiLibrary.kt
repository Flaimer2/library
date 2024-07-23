package ru.snapix.library

import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.platform.AudienceProvider
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.Plugin
import org.jetbrains.exposed.sql.Database
import ru.snapix.library.network.Platform

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
}

val adventure get() = SnapiLibrary.adventure()