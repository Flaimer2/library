package ru.snapix.library.menu.panels

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.menu.*
import kotlin.time.Duration

abstract class BukkitPanel internal constructor(
    val player: Player,
    override val title: String,
    override val update: Duration?,
    override val replacements: List<Replacement>
) : Panel, InventoryHolder {
    abstract val bukkitInventory: Inventory
    abstract val updateTimer: BukkitTask?

    open fun render(itemMap: ItemMap) {
        for (slot in 0..<inventory.size) {
            val item = itemMap[slot]?.clone()
            if (item != null && item.condition(Conditions(this, item, slot))) {
                item.replace(player, replacements)
                inventory.setItem(slot, item.item())
            } else {
                inventory.setItem(slot, null)
            }
        }

        player.updateInventory()
    }


    override fun disable() {
        updateTimer?.cancel()
    }

    fun open() {
        onOpen()
        player.openInventory(inventory)
    }

    fun runClickCallbacks(slot: Int, type: ClickType) {
        val item = getItemBySlot(slot) ?: return
        val clickAction = item.clickAction ?: return

        val click = Click(player, this, type, item, slot)

        click.clickAction()
    }

    abstract fun getItemBySlot(slot: Int): Item?

    override fun getInventory(): Inventory {
        return bukkitInventory
    }
}