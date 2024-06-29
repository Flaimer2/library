package ru.snapix.library

import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin

@Plugin(id = "snapilibrary", name = "SnapiLibrary", version = "1.5", authors = ["Flaimer"], dependencies = [Dependency(id = "lastloginapi", optional = true)])
class SnapiLibraryVelocity {
    init {
        SnapiLibrary.platform = Platform.VELOCITY
    }
}