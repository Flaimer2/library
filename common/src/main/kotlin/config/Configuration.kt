package ru.snapix.library.config

import space.arim.dazzleconf.ConfigurationOptions
import space.arim.dazzleconf.error.ConfigFormatSyntaxException
import space.arim.dazzleconf.error.InvalidConfigException
import space.arim.dazzleconf.ext.snakeyaml.CommentMode
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions
import space.arim.dazzleconf.helper.ConfigurationHelper
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Path

class Configuration<C> private constructor(private val configHelper: ConfigurationHelper<C>) {
    private var configData: C? = null

    init {
        reloadConfig()
    }

    fun reloadConfig() {
        try {
            configData = configHelper.reloadConfigData()
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        } catch (ex: ConfigFormatSyntaxException) {
            configData = configHelper.factory.loadDefaults()
            println("The yaml syntax in your configuration is invalid. $ex")
        } catch (ex: InvalidConfigException) {
            configData = configHelper.factory.loadDefaults()
            println("One of the values in your configuration is not valid. $ex")
        }
    }

    fun data(): C {
        if (configData == null) {
            reloadConfig()
        }

        return configData!!
    }

    companion object {
        fun <C> create(
            fileName: String,
            configFolder: Path,
            configClass: Class<C>,
            options: ConfigurationOptions = ConfigurationOptions.defaults(),
        ): Configuration<C> {
            val yamlOptions = SnakeYamlOptions.Builder()
                .commentMode(CommentMode.alternativeWriter())
                .build()
            val configFactory = SnakeYamlConfigurationFactory.create(configClass, options, yamlOptions)
            return Configuration(ConfigurationHelper(configFolder, fileName, configFactory))
        }
    }
}