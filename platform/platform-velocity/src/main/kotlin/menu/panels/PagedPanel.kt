package ru.snapix.library.menu.panels

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.scheduler.ScheduledTask
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.data.inventory.InventoryType
import ru.snapix.library.menu.*
import ru.snapix.library.repeatTask
import ru.snapix.library.snapiLibrary
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration

class PagedPanel internal constructor(
    player: Player,
    title: String,
    update: Duration?,
    replacements: Replacement,
    val pages: List<Layout>,
    items: List<Item>,
    updateReplacements: (String) -> String
) : VelocityPanel(player, title, update, replacements, updateReplacements) {
    override val inventory: Inventory
    override val updateTimer: ScheduledTask?
    private val pageMap = PageList()
    private val currentPage = AtomicInteger(0)

    init {
        inventory = Inventory(InventoryType.chestInventoryWithRows(pages[0].size))
        inventory.title(title.toChatElement())
        items.forEach { item ->
            for (page in pages.withIndex()) {
                for (line in page.value.withIndex()) {
                    for (slot in line.value.withIndex()) {
                        if (slot.value == item.index) {
                            pageMap[page.index, 9 * line.index + slot.index] = item
                        }
                    }
                }
            }
        }
        replacements["current_page"] = { getCurrentPage() }
        replacements["current_display_page"] = { getCurrentPage() + 1 }
        replacements["next_page"] = { getCurrentPage() + 1 }
        replacements["next_display_page"] = { getCurrentPage() + 2 }

        render()
        onOpen()

        updateTimer = if (update != null) {
            snapiLibrary.server.repeatTask(update) { render() }
        } else {
            null
        }

        // register listeners
        inventory.onClick {
            it.cancelled(true)
            runClickCallbacks(it.slot(), it.clickType())
        }
        inventory.onClose {
            onClose()
        }
    }

    fun render() {
        render(getCurrentItemMap())
    }

    override fun getItemBySlot(slot: Int): Item? {
        return getCurrentItemMap()[slot]
    }

    fun getCurrentItemMap(): ItemMap {
        return getItemMap(getCurrentPage())
    }

    fun setPage(page: Int) {
        currentPage.set(page)
        render()
    }

    fun getItemMap(page: Int): ItemMap {
        return pageMap.getItemMap(page) ?: emptyItemMap()
    }

    fun getLastPage(): Int {
        return pages.size - 1
    }

    fun getCurrentPage(): Int {
        return currentPage.get()
    }

    override fun onOpen() {}

    override fun onClose() {
        disable()
    }
}