package ru.snapix.library.menu

import ru.snapix.library.menu.panels.VelocityPanel

typealias Condition = Conditions.() -> Boolean

data class Conditions(val inventory: VelocityPanel, val item: Item, val slot: Int)