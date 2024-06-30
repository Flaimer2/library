package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.ClickType

data class Click(val player: Player, val type: ClickType, val clickedItem: Item, val slot: Int)

typealias ClickAction = Click.() -> Unit