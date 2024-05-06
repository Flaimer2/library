package ru.snapix.library

import org.bukkit.plugin.java.JavaPlugin
import space.arim.dazzleconf.ConfigurationOptions
import java.nio.file.Path

fun <C> Configuration.Companion.create(
    fileName: String,
    configClass: Class<C>,
    plugin: JavaPlugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, plugin.dataFolder.toPath(), configClass, plugin.slF4JLogger, options)
}

fun <C> Configuration.Companion.create(
    fileName: String,
    configFolder: Path,
    configClass: Class<C>,
    plugin: JavaPlugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, configFolder, configClass, plugin.slF4JLogger, options)
}