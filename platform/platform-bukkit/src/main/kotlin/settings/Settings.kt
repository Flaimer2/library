package ru.snapix.library.bukkit.settings

import ru.snapix.library.bukkit.plugin
import ru.snapix.library.settings.Configuration

object Settings {
    private val mainConfig = Configuration.create("config.yml", MainConfig::class.java, plugin)
    val config get() = mainConfig.data()
}