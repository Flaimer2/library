package ru.snapix.library.extensions

import org.bukkit.plugin.Plugin
import ru.snapix.library.settings.Configuration
import space.arim.dazzleconf.ConfigurationOptions
import java.nio.file.Path

fun <C> Configuration.Companion.create(
    fileName: String,
    configClass: Class<C>,
    plugin: Plugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, plugin.dataFolder.toPath(), configClass, plugin.slF4JLogger, options)
}

fun <C> Configuration.Companion.create(
    fileName: String,
    configFolder: Path,
    configClass: Class<C>,
    plugin: Plugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, configFolder, configClass, plugin.slF4JLogger, options)
}