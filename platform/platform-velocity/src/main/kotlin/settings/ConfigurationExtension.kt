package ru.snapix.library.velocity.settings

import ru.snapix.library.settings.Configuration
import ru.snapix.library.velocity.VelocityPlugin
import space.arim.dazzleconf.ConfigurationOptions

fun <C> Configuration.Companion.create(
    fileName: String,
    configClass: Class<C>,
    plugin: VelocityPlugin,
    options: ConfigurationOptions = ConfigurationOptions.defaults(),
): Configuration<C> {
    return create(fileName, plugin.dataDirectory, configClass, options)
}