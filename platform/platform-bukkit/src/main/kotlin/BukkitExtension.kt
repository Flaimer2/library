package ru.snapix.library

import org.bukkit.Bukkit
import org.bukkit.event.Event

fun callEvent(event: Event) {
    Bukkit.getPluginManager().callEvent(event)
}