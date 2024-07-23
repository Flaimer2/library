package ru.snapix.library.bukkit.panel

import ru.snapix.library.bukkit.panel.type.BukkitPanel

typealias Condition = Conditions.() -> Boolean

data class Conditions(val inventory: BukkitPanel, val item: Item, val slot: Int)