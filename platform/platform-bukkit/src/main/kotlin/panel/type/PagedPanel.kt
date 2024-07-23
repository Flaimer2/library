package ru.snapix.library.bukkit.panel.type

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.Replacement
import ru.snapix.library.bukkit.panel.Item
import ru.snapix.library.bukkit.panel.ItemMap
import ru.snapix.library.bukkit.panel.PageList
import ru.snapix.library.bukkit.panel.emptyItemMap
import ru.snapix.library.bukkit.plugin
import ru.snapix.library.network.panel.Layout
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

        render()
        player.openInventory(inventory)
        onOpen()

        updateTimer = if (update != null) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                { render() },
                update.inWholeMilliseconds,
                update.inWholeMilliseconds
            )
        } else {
            null
        }
    }

    override fun render() {
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