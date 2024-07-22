package ru.snapix.library.menu.panels

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.menu.Item
import ru.snapix.library.menu.Layout
import ru.snapix.library.menu.Replacement
import ru.snapix.library.menu.emptyItemMap
import ru.snapix.library.snapiLibrary
import kotlin.time.Duration

class StandardPanel internal constructor(
    player: Player,
    title: String,
    update: Duration?,
    replacements: Replacement,
    layout: Layout,
    items: List<Item>,
    updateReplacements: (String) -> String
) : BukkitPanel(player, title, update, replacements, updateReplacements) {
    override val bukkitInventory: Inventory = Bukkit.createInventory(this, layout.size * 9, title)
    override val updateTimer: BukkitTask?
    private val itemMap = emptyItemMap()

    init {
        items.forEach { item ->
            for (line in layout.withIndex()) {
                for (slot in line.value.withIndex()) {
                    if (slot.value == item.index) {
                        itemMap[9 * line.index + slot.index] = item
                    }
                }
            }
        }

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
        render(itemMap)
    }

    override fun onOpen() {}

    override fun onClose() {
        disable()
    }

    override fun getItemBySlot(slot: Int): Item? {
        return itemMap[slot]
    }
}