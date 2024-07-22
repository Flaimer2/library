package ru.snapix.library

import com.google.inject.Inject
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.snapix.library.velocity.VelocityPlugin
import java.nio.file.Path

@Plugin(
    id = "snapilibrary",
    name = "SnapiLibrary",
    version = "1.11.2",
    authors = ["Flaimer"],
    dependencies = [Dependency(id = "lastloginapi", optional = true)]
)
class SnapiLibraryVelocity @Inject constructor(
    server: ProxyServer,
    logger: Logger,
    @DataDirectory dataDirectory: Path
) : VelocityPlugin() {
    init {
        instance = this
        init(server, logger, dataDirectory)
        SnapiLibrary.platform = Platform.VELOCITY
    }

    companion object {
        @JvmStatic
        lateinit var instance: SnapiLibraryVelocity
            private set
    }
}

val snapiLibrary = SnapiLibraryVelocity.instance