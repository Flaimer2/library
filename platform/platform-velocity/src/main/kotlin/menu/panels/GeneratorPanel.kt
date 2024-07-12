package ru.snapix.library.menu.panels

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.scheduler.ScheduledTask
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.data.inventory.InventoryType
import dev.simplix.protocolize.data.packets.SetSlot
import ru.snapix.library.menu.*
import ru.snapix.library.repeatTask
import ru.snapix.library.snapiLibrary
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ceil
import kotlin.time.Duration

class GeneratorPanel<T> internal constructor(
    player: Player,
    title: String,
    update: Duration?,
    replacements: List<Replacement>,
    val layout: Layout,
    items: List<Item>,
    var generatorSource: () -> List<T>,
    var generatorOutput: (T) -> Item?,
    var filter: (T) -> Boolean,
    var comparator: Comparator<T>
) : VelocityPanel(player, title, update, replacements) {
    override val inventory: Inventory
    override val updateTimer: ScheduledTask?
    private val itemMap = emptyItemMap()
    private val currentPage = AtomicInteger(0)
    private val lastPage = AtomicInteger(0)

    init {
        inventory = Inventory(InventoryType.chestInventoryWithRows(layout.size))
        inventory.title(title.toChatElement())
        items.forEach { item ->
            for (line in layout.withIndex()) {
                for (slot in line.value.withIndex()) {
                    if (slot.value == item.index) {
                        itemMap[9 * line.index + slot.index] = item
                    }
                }
            }
        }

        updateTimer = if (update != null) {
            snapiLibrary.server.repeatTask(update) { render() }
        } else {
            render()
            null
        }

        // register listeners
        inventory.onClick {
            runClickCallbacks(it.slot(), it.clickType())
        }
        inventory.onClose {
            onClose()
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
        }
        val player = Protocolize.playerProvider().player(player.uniqueId)

        var windowId = -1

        for (id in player.registeredInventories().keys) {
            val value = player.registeredInventories()[id]
            if (value == inventory) {
                windowId = id
                break
            }
        }
        if (windowId == -1) return

        val size = layout.size * 9 - itemMap.size
        val currentPage = if (getCurrentPage() < 0) 0 else getCurrentPage()
        val generatorSource = generatorSource().filter(filter).sortedWith(comparator)
        val generator = generatorSource.drop(currentPage * size).take(size).iterator()

        lastPage.set(ceil(generatorSource.size / size.toFloat()).toInt() - 1)

        for (slot in 0..<inventory.size) {
            val item = itemMap[slot]?.clone()
            if (item != null) {
                replace(item)
                inventory.item(slot, item.item())
                player.sendPacket(SetSlot(windowId.toByte(), slot.toShort(), item.item(), 1))
            } else {
                if (generator.hasNext()) {
                    val value = generator.next()
                    val itemGen = generatorOutput(value)
                    if (itemGen != null) {
                        replace(itemGen)
                        inventory.item(slot, itemGen.item())
                        player.sendPacket(SetSlot(windowId.toByte(), slot.toShort(), itemGen.item(), 1))
                    }
                } else {
                    inventory.removeItem(slot)
                    player.sendPacket(SetSlot(windowId.toByte(), slot.toShort(), null, 1))
                }
            }
        }
    }

    override fun getItemBySlot(slot: Int): Item? {
        return itemMap[slot]
    }

    override fun onOpen() {}

    override fun onClose() {
        disable()
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