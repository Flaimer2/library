package ru.snapix.library.bukkit.utils

import org.bukkit.Bukkit
import org.bukkit.event.Event

fun callEvent(event: Event) {
    Bukkit.getPluginManager().callEvent(event)
}