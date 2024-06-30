package ru.snapix.library.menu

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import ru.snapix.library.runTaskTimer
import kotlinx.coroutines.channels.ReceiveChannel
import org.bukkit.event.inventory.ClickType
import kotlin.time.Duration

class BukkitInventory : InventoryHolder {
    private val players = mutableSetOf<Player>()
    private val bukkitInventory: Inventory
    private val itemMap = emptyItemMap()
    private val replacements: List<Replacement>
    private val updateTimer: ReceiveChannel<Unit>?

    internal constructor(title: String = "DefaultSnapTitle", items: List<Item> = emptyList(), layout: List<String> = emptyList(), replacements: List<Replacement> = emptyList(), update: Duration? = null) {
        bukkitInventory = Bukkit.createInventory(this, layout.size * 9, title)

        items.forEach { item ->
            for (line in layout.withIndex()) {
                for (slot in line.value.withIndex()) {
                    if (slot.value == item.index) {
                        itemMap[9 * line.index + slot.index] = item
                    }
                }
            }
        }
        this.replacements = replacements

        updateTimer = if (update != null) {
            runTaskTimer(update) { update() }
        } else {
            null
        }
    }

    fun disable() {
        updateTimer?.cancel()
    }

    internal fun onOpen(player: Player) {
        players.add(player)
        update()
    }

    internal fun onClose(player: Player) {
        players.remove(player)
    }

    internal fun click(player: Player, slot: Int, type: ClickType) {
        val item = itemMap[slot] ?: return
        val clickAction = item.clickAction ?: return

        val click = Click(player, type, item, slot)

        click.clickAction()
    }

    fun update() {
        for (viewer in players) {
            val items = itemMap.mapValues { it.value.clone() }
            for ((slot, item) in items) {
                for (replace in replacements) {
                    item.name = item.name?.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true)
                    item.lore = item.lore.map { it.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true) }
                }
                item.name?.let { item.name = PlaceholderAPI.setPlaceholders(viewer, it) }
                item.lore = item.lore.map { PlaceholderAPI.setPlaceholders(viewer, it) }
                inventory.setItem(slot, item.item())
            }
        }
    }

    fun openForPlayer(player: Player) {
        player.openInventory(inventory)
    }

    override fun getInventory(): Inventory {
        return bukkitInventory
    }
}