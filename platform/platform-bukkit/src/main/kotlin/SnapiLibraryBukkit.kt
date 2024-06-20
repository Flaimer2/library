package ru.snapix.library

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin

class SnapiLibraryBukkit : JavaPlugin() {
    lateinit var economy: Economy
    init {
        SnapiLibrary.platform = Platform.BUKKIT
    }

    override fun onEnable() {
        instance = this
        setupEconomy()
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

    companion object {
        @JvmStatic
        lateinit var instance: SnapiLibraryBukkit
            private set
    }
}

val snapiLibraryBukkit = SnapiLibraryBukkit.instance