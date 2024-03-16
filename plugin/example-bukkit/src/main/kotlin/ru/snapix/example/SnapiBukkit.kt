package ru.snapix.example

import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.library.SnapiLibraryBukkit
import ru.snapix.library.database.Database
import ru.snapix.library.database.initializeDatabase
import ru.snapix.library.extensions.create
import ru.snapix.library.settings.Configuration

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
        Configuration.create("mysql.yml", SnapiLibraryBukkit::class.java, this)
    }

    override fun onDisable() {
        database.close()
    }

    companion object {
        lateinit var instance: SnapiBukkit
    }
}