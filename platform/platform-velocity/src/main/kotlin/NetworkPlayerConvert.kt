package ru.snapix.library

import com.velocitypowered.api.proxy.Player
import kotlin.jvm.optionals.getOrNull

fun NetworkPlayer.toPlayer(): Player? {
    return snapiLibrary.server.getPlayer(name()).getOrNull()
}