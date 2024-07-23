package ru.snapix.library.bukkit.panel.type

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.Replacement
import ru.snapix.library.bukkit.panel.Item
import ru.snapix.library.bukkit.panel.emptyItemMap
import ru.snapix.library.bukkit.plugin
import ru.snapix.library.network.panel.Layout
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