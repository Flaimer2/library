package ru.snapix.library.menu.panels

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.menu.*
import ru.snapix.library.snapiLibrary
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration

class PagedPanel internal constructor(
    player: Player,
    title: String,
    update: Duration?,
    val pages: List<Layout>,
    items: List<Item>,
    override var replacements: Replacement,
    updateReplacements: (String) -> String
) : BukkitPanel(player, title, update, replacements, updateReplacements) {
    override val bukkitInventory: Inventory = Bukkit.createInventory(this, pages[0].size * 9, title)
    override val updateTimer: BukkitTask?
    private val pageMap = PageList()
    private val currentPage = AtomicInteger(0)

    init {
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

        player.openInventory(inventory)
        onOpen()

        updateTimer = if (update != null) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(snapiLibrary, { render() }, 0L, update.inWholeMilliseconds)
        } else {
            render()
            null
        }
    }

    fun render() {
        render(getCurrentItemMap())
    }

    override fun onOpen() {}

    override fun onClose() {
        disable()
    }

    override fun getItemBySlot(slot: Int): Item? {
        return getCurrentItemMap()[slot]
    }

    fun getCurrentPage(): Int {
        return currentPage.get()
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
}