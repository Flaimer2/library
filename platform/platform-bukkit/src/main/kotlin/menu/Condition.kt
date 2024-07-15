package ru.snapix.library.menu

import ru.snapix.library.menu.panels.BukkitPanel

typealias Condition = Conditions.() -> Boolean

data class Conditions(val inventory: BukkitPanel, val item: Item, val slot: Int)