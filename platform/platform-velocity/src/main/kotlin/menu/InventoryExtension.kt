package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.chat.ChatElement
import dev.simplix.protocolize.api.inventory.Inventory
import ru.snapix.library.utils.translateAlternateColorCodes

fun String.toChatElement(): ChatElement<String> {
    return ChatElement.ofLegacyText(translateAlternateColorCodes(this))
}

val Inventory.size: Int
    get() = type().getTypicalSize(47)

fun Player.closeInventory() {
    val player = Protocolize.playerProvider().player(uniqueId) ?: return
    player.closeInventory()
}
