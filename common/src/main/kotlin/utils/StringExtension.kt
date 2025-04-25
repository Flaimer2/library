package ru.snapix.library.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

fun String.replaceLast(oldValue: String, newValue: String): String {
    return replaceFirst("(?s)$oldValue(?!.*?$oldValue)".toRegex(), newValue)
}

fun String.toComponent(): TextComponent {
    return Component.text(this)
}
