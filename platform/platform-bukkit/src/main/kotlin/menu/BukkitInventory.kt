package ru.snapix.library.menu

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import ru.snapix.library.snapiLibrary

// TODO: Add base class of Inventory (module-common)
open class BukkitInventory(title: String = "DefaultSnapTitle", private var layout: List<String> = listOf(), private val items: List<Item> = emptyList(), private var replacements: List<Pair<String, () -> Any>> = emptyList(), private var update: Int) : InventoryHolder {
    val bukkitInventory: Inventory = Bukkit.createInventory(this, layout.size * 9, title)

    init {
        // TODO: Make load replacements on load!!!
        items.forEach { item ->
            for (line in layout.withIndex()) {
                for (slot in line.value.withIndex()) {
                    if (slot.value == item.index) {
                        bukkitInventory.setItem(9 * line.index + slot.index, item.item())
                    }
                }
            }
        }
    }

    override fun getInventory(): Inventory {
        return bukkitInventory
    }

    private fun getItemOnSlot(slotIndex: Int): Item? {
        var char: Char? = null
        for (line in layout.withIndex()) {
            for (slot in line.value.withIndex()) {
                if (9 * line.index + slot.index == slotIndex) {
                    char = slot.value
                }
            }
        }
        return items.find { it.index == char }
    }

    fun executeClickAction(action: ClickAction) {
        getItemOnSlot(action.slot)?.clickAction?.let {
            it(action)
        }
    }

    private fun update(player: Player) {
        val inventory = player.openInventory.topInventory ?: return

        if (inventory.holder is BukkitInventory) {
            val map = mutableMapOf<Int, ItemStack>()
            val items = items.map { it.clone() }
            items.forEach { item ->
                replacements.forEach {
                    item.name = item.name?.replace("{${it.first}}", it.second().toString(), ignoreCase = true)
                }
                replacements.forEach { replace ->
                    item.lore = item.lore.map {
                        it.replace(
                            "{${replace.first}}",
                            replace.second().toString(),
                            ignoreCase = true
                        )
                    }.toMutableList()
                }
                item.lore = item.lore.map { PlaceholderAPI.setPlaceholders(player, it) }.toMutableList()
                for (line in layout.withIndex()) {
                    for (slot in line.value.withIndex()) {
                        if (slot.value == item.index) {
                            map[9 * line.index + slot.index] = item.item()
                        }
                    }
                }
            }
            map.forEach {
                inventory.setItem(it.key, it.value)
            }
        }
    }

    fun openFor(player: Player) {
        player.openInventory(bukkitInventory)
        // TODO: DONT MAKE THIS (Maybe do by viewers update???)
        Bukkit.getScheduler().runTaskTimerAsynchronously(snapiLibrary, { update(player) }, 1L, update.toLong())
    }
}