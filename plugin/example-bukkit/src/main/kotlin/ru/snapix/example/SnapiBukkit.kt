package ru.snapix.example

import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.library.common.database.Database
import ru.snapix.library.common.database.initializeDatabase

class SnapiBukkit : JavaPlugin() {
    private lateinit var database: Database
    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        database = initializeDatabase {
            host = "localhost"
            port = 3306
            database = "server_global"
            username = "root"
            password = "root"
        }
    }

    override fun onDisable() {
        database.close()
    }

    companion object {
        lateinit var instance: SnapiBukkit
    }
}