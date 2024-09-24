package ru.snapix.library.bukkit.panel.type

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask
import ru.snapix.library.Replacement
import ru.snapix.library.bukkit.panel.Conditions
import ru.snapix.library.bukkit.panel.Item
import ru.snapix.library.bukkit.panel.ItemMap
import ru.snapix.library.bukkit.panel.emptyItemMap
import ru.snapix.library.bukkit.plugin
import ru.snapix.library.network.panel.Layout
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ceil
import kotlin.time.Duration

class GeneratorPanel<T> internal constructor(
    player: Player,
    title: String,
    update: Duration?,
    replacements: Replacement,
    val layout: Layout,
    items: List<Item>,
    updateReplacements: (String) -> String,
    var generatorSource: () -> List<T>,
    var generatorOutput: (T) -> Item?,
    var filter: (T) -> Boolean,
    var comparator: Comparator<T>
) : BukkitPanel(player, title, update, replacements, updateReplacements) {
    override val bukkitInventory: Inventory = Bukkit.createInventory(this, layout.size * 9, title)
    override val updateTimer: BukkitTask?
    private val itemMap = emptyItemMap()
    private val clickItemMap = emptyItemMap()
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
                update.inWholeSeconds * 20,
                update.inWholeSeconds * 20
            )
        } else {
            null
        }
    }

    override fun render() {
        render(itemMap)
    }

    override fun render(itemMap: ItemMap) {
        val size = layout.size * 9 - itemMap.size
        val currentPage = if (getCurrentPage() < 0) 0 else getCurrentPage()
        val generatorSource = generatorSource().filter(filter).sortedWith(comparator)
        val generator = generatorSource.drop(currentPage * size).take(size).iterator()

        lastPage.set(ceil(generatorSource.size / size.toFloat()).toInt() - 1)

        val replacements = replacements.mapValues { it.value().toString() }.toMutableMap()

        for (slot in 0..<inventory.size) {
            val item = itemMap[slot]?.clone()
            if (item != null) {
                if (!item.condition(Conditions(this, item, slot))) {
                    inventory.setItem(slot, null)
                    clickItemMap[slot] = Item()
                    continue
                }
                item.replace(player, replacements, updateReplacements)
                inventory.setItem(slot, item.item())
                clickItemMap[slot] = item
            } else {
                if (generator.hasNext()) {
                    val value = generator.next()
                    val itemGen = generatorOutput(value)
                    if (itemGen != null && itemGen.condition(Conditions(this, itemGen, slot))) {
                        itemGen.replace(player, replacements, updateReplacements)
                        inventory.setItem(slot, itemGen.item())
                        clickItemMap[slot] = itemGen
                    }
                } else {
                    inventory.setItem(slot, null)
                    clickItemMap[slot] = Item()
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
        return clickItemMap [slot]
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