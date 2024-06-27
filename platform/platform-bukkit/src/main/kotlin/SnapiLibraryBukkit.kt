package ru.snapix.library

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.library.plugin.Settings

class SnapiLibraryBukkit : JavaPlugin() {
    private var adventure: BukkitAudiences? = null
    lateinit var economy: Economy
    lateinit var serverType: ServerType

    init {
        SnapiLibrary.platform = Platform.BUKKIT
    }

    override fun onLoad() {
        instance = this
        serverType = Settings.config.gameType()
    }

    override fun onEnable() {
        adventure = BukkitAudiences.create(this)
        setupEconomy()
    }

    override fun onDisable() {
        adventure?.let {
            it.close()
            adventure = null
        }
    }

    private fun setupEconomy() {
        if (server.pluginManager.getPlugin("Vault") == null) {
            return
        }
        val rsp = server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return
        economy = rsp.provider
    }

    fun adventure(): BukkitAudiences {
        checkNotNull(adventure) { "Tried to access Adventure when the plugin was disabled!" }
        return adventure as BukkitAudiences
    }


    companion object {
        @JvmStatic
        lateinit var instance: SnapiLibraryBukkit
            private set
    }
}

val snapiLibrary = SnapiLibraryBukkit.instance
val adventure
    get() = snapiLibrary.adventure()