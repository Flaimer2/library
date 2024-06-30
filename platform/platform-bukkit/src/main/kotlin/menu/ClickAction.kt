package ru.snapix.library.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

data class Click(val player: Player, val type: ClickType, val clickedItem: Item, val slot: Int)

typealias ClickAction = Click.() -> Unit