package ru.snapix.library.bukkit.settings

import ru.snapix.library.network.ServerType
import space.arim.dazzleconf.annote.ConfDefault.DefaultString

interface MainConfig {
    @DefaultString("UNKNOWN")
    fun gameType(): ServerType
}