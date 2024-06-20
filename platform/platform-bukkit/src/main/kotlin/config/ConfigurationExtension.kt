package ru.snapix.library.config

import org.bukkit.plugin.java.JavaPlugin
import space.arim.dazzleconf.ConfigurationOptions

fun <C> Configuration.Companion.create(
    fileName: String,
    configClass: Class<C>,
    plugin: JavaPlugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, plugin.dataFolder.toPath(), configClass, options)
}