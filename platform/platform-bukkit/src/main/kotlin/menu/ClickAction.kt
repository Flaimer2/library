package ru.snapix.library.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

data class ClickAction(val player: Player, val type: ClickType, val clickedItem: ItemStack, val slot: Int)