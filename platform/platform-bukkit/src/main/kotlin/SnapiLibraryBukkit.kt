package ru.snapix.library.bukkit

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.bukkit.panel.InventoryListener
import ru.snapix.library.bukkit.panel.type.BukkitPanel
import ru.snapix.library.bukkit.settings.Settings
import ru.snapix.library.network.ServerType

class SnapiLibraryBukkit : JavaPlugin() {
    lateinit var economy: Economy
    lateinit var serverType: ServerType

    override fun onLoad() {
        instance = this
        serverType = Settings.config.gameType()
    }

    override fun onEnable() {
        SnapiLibrary.initBukkit(this)
        setupEconomy()
        server.pluginManager.registerEvents(InventoryListener(), this)
    }

    override fun onDisable() {
        server.onlinePlayers.forEach { p ->
            if (p.openInventory.topInventory.holder is BukkitPanel)
                p.closeInventory()
        }
        SnapiLibrary.disableBukkit()
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

val plugin = SnapiLibraryBukkit.instance