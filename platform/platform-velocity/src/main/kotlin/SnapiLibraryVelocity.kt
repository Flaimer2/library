package ru.snapix.library

import com.velocitypowered.api.plugin.Plugin

@Plugin(id = "snapilibrary", name = "SnapiLibrary", version = "1.1", authors = ["Flaimer"])
class SnapiLibraryVelocity {
    init {
        SnapiLibrary.platform = Platform.VELOCITY
    }
}