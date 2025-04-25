package ru.snapix.library.bukkit.settings

import org.bukkit.plugin.java.JavaPlugin
import ru.snapix.library.settings.Configuration
import space.arim.dazzleconf.ConfigurationOptions

fun <C> Configuration.Companion.create(
    fileName: String,
    configClass: Class<C>,
    plugin: JavaPlugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, plugin.dataFolder.toPath(), configClass, options)
}