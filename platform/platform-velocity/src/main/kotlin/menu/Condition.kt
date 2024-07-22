package ru.snapix.library.menu

import ru.snapix.library.menu.panels.GeneratorPanel
import ru.snapix.library.menu.panels.PagedPanel
import ru.snapix.library.menu.panels.VelocityPanel

typealias Condition = Conditions.() -> Boolean

data class Conditions(val inventory: VelocityPanel, val item: Item, val slot: Int)

fun Conditions.prevPage(): Boolean {
    if (inventory is PagedPanel) {
        val page = inventory.getCurrentPage()
        return page > 0
    }
    if (inventory is GeneratorPanel<*>) {
        val page = inventory.getCurrentPage()
        return page > 0
    }
    return false
}

fun Conditions.nextPage(): Boolean {
    if (inventory is PagedPanel) {
        val lastPage = inventory.getLastPage()
        val nextPage = inventory.getCurrentPage() + 1
        return lastPage >= nextPage
    }
    if (inventory is GeneratorPanel<*>) {
        val lastPage = inventory.getLastPage()
        val nextPage = inventory.getCurrentPage() + 1
        return lastPage >= nextPage
    }
    return false
}
