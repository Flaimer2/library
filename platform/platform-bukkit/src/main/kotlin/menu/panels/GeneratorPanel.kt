package ru.snapix.library.menu.panels

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.menu.*
import ru.snapix.library.menu.items.Item
import ru.snapix.library.snapiLibrary
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ceil
import kotlin.time.Duration

class GeneratorPanel<T> internal constructor(
    player: Player,
    title: String = "Kotlin Generator Panel",
    update: Duration? = null,
    replacements: MutableList<Replacement> = mutableListOf(),
    val layout: Layout,
    items: List<Item> = emptyList(),
    var generatorSource: () -> List<T> = { emptyList() },
    var generatorOutput: (T) -> Item? = { null },
    var filter: (T) -> Boolean = { true },
    var comparator: Comparator<T> = compareBy { 0 }
) : BukkitPanel(player, title, update, replacements) {
    override val bukkitInventory: Inventory = Bukkit.createInventory(this, layout.size * 9, title)
    override val updateTimer: BukkitTask?
    private val itemMap = emptyItemMap()
    private val currentPage = AtomicInteger(0)
    private val lastPage = AtomicInteger(0)

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
        replacements.add("current_page" to { getCurrentPage() })
        replacements.add("current_display_page" to { getCurrentPage() + 1 })
        replacements.add("next_page" to { getCurrentPage() + 1 })
        replacements.add("next_display_page" to { getCurrentPage() + 2 })
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

    override fun render(itemMap: ItemMap) {
        fun replace(item: Item) {
            for (replace in replacements) {
                item.name = item.name?.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true)
                item.lore = item.lore.map { it.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true) }
            }
            item.name?.let {
                item.name = PlaceholderAPI.setPlaceholders(player, it)
            }

            item.lore = item.lore.map { PlaceholderAPI.setPlaceholders(player, it) }
        }

        val size = layout.size * 9 - itemMap.size
        val currentPage = if (getCurrentPage() < 0) 0 else getCurrentPage()
        val generatorSource = generatorSource().filter(filter).sortedWith(comparator)
        val generator = generatorSource.drop(currentPage * size).take(size).iterator()

        lastPage.set(ceil(generatorSource.size / size.toFloat()).toInt() - 1)

        for (slot in 0..<inventory.size) {
            val item = itemMap[slot]?.clone()
            if (item != null) {
                replace(item)
                inventory.setItem(slot, item.item())
            } else {
                if (generator.hasNext()) {
                    val value = generator.next()
                    val itemGen = generatorOutput(value)
                    if (itemGen != null) {
                        replace(itemGen)
                        inventory.setItem(slot, itemGen.item())
                    }
                } else {
                    inventory.setItem(slot, null)
                }
            }
        }

        player.updateInventory()
    }

    override fun onOpen() {}

    override fun onClose() {
        disable()
    }

    override fun getItemBySlot(slot: Int): Item? {
        return itemMap[slot]
    }

    fun getCurrentPage(): Int {
        return currentPage.get()
    }

    fun setPage(page: Int) {
        currentPage.set(page)
        render()
    }

    fun getLastPage(): Int {
        return lastPage.get()
    }
}