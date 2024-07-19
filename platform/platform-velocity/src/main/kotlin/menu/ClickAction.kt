package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.ClickType
import ru.snapix.library.menu.panels.GeneratorPanel
import ru.snapix.library.menu.panels.PagedPanel
import ru.snapix.library.menu.panels.VelocityPanel

data class Click(
    val player: Player,
    val inventory: VelocityPanel,
    val type: ClickType,
    val clickedItem: Item,
    val slot: Int
)

typealias ClickAction = Click.() -> Unit

fun Click.nextPage() {
    if (inventory is PagedPanel) {
        val lastPage = inventory.getLastPage()
        val nextPage = inventory.getCurrentPage() + 1
        if (lastPage >= nextPage) {
            inventory.setPage(nextPage)
        }
    }
    if (inventory is GeneratorPanel<*>) {
        val lastPage = inventory.getLastPage()
        val nextPage = inventory.getCurrentPage() + 1
        if (lastPage >= nextPage) {
            inventory.setPage(nextPage)
        }
    }
}

fun Click.prevPage() {
    if (inventory is PagedPanel) {
        val page = inventory.getCurrentPage()
        if (page > 0) {
            inventory.setPage(page - 1)
        }
    }
    if (inventory is GeneratorPanel<*>) {
        val page = inventory.getCurrentPage()
        if (page > 0) {
            inventory.setPage(page - 1)
        }
    }
}