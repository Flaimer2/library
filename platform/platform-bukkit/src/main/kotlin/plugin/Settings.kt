package ru.snapix.library.plugin

import ru.snapix.library.config.Configuration
import ru.snapix.library.config.create
import ru.snapix.library.snapiLibrary

object Settings {
    private val mainConfig = Configuration.create("config.yml", MainConfig::class.java, snapiLibrary)
    val config get() = mainConfig.data()
}