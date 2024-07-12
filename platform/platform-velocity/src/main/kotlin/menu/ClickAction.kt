package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.ClickType
import ru.snapix.library.menu.panels.VelocityPanel

data class Click(val player: Player, val panel: VelocityPanel, val type: ClickType, val clickedItem: Item, val slot: Int)

typealias ClickAction = Click.() -> Unit