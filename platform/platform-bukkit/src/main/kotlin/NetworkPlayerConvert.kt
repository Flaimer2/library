package ru.snapix.library

import org.bukkit.entity.Player

fun Player.toNetworkPlayer(): NetworkPlayer {
    return NetworkPlayer(name)
}