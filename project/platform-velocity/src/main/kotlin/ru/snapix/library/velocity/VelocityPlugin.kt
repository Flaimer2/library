package ru.snapix.library.velocity

import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Path

@Suppress("Unused", "MemberVisibilityCanBePrivate")
class VelocityPlugin {
    lateinit var name: String
    lateinit var version: String
    lateinit var server: ProxyServer
    lateinit var logger: Logger
    lateinit var dataDirectory: Path

    fun init(server: ProxyServer, logger: Logger, @DataDirectory dataDirectory: Path) {
        this.name = javaClass.getAnnotation(Plugin::class.java).name
        this.version = javaClass.getAnnotation(Plugin::class.java).version
        this.server = server
        this.logger = logger
        this.dataDirectory = dataDirectory
    }
}