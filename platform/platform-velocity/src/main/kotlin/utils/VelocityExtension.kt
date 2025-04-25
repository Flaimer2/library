package ru.snapix.library.velocity.utils

import ru.snapix.library.velocity.plugin

fun <E> callEvent(event: E) {
    plugin.server.eventManager.fire(event)
}