package ru.snapix.library.menu

import dev.simplix.protocolize.api.chat.ChatElement
import ru.snapix.library.utils.translateAlternateColorCodes

fun String.toChatElement(): ChatElement<String> {
    return ChatElement.ofLegacyText(translateAlternateColorCodes(this))
}