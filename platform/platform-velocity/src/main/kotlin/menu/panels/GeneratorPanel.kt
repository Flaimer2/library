package ru.snapix.library.menu.panels

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.scheduler.ScheduledTask
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.BaseItemStack
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.inventory.InventoryType
import dev.simplix.protocolize.data.packets.OpenWindow
import dev.simplix.protocolize.data.packets.SetSlot
import dev.simplix.protocolize.data.packets.WindowItems
import ru.snapix.library.menu.*
import ru.snapix.library.menu.dsl.Material
import ru.snapix.library.repeatTask
import ru.snapix.library.snapiLibrary
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
    var generatorSource: () -> List<T>,
    var generatorOutput: (T) -> Item?,
    var filter: (T) -> Boolean,
    var comparator: Comparator<T>,
    override val updateReplacements: (String) -> String
) : VelocityPanel(player, title, update, replacements, updateReplacements) {
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
        replacements["current_page"] = { getCurrentPage() }
        replacements["current_display_page"] = { getCurrentPage() + 1 }
        replacements["next_page"] = { getCurrentPage() + 1 }
        replacements["next_display_page"] = { getCurrentPage() + 2 }

        render()
        onOpen()

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
        val player = Protocolize.playerProvider().player(player.uniqueId) ?: return

        var windowId = -1
        var alreadyOpen = false

        for (id in player.registeredInventories().keys) {
            val value = player.registeredInventories()[id]
            if (value == inventory) {
                windowId = id
                alreadyOpen = true
                break
            }
        }

        if (windowId == -1) {
            windowId = player.generateWindowId()
            player.registerInventory(windowId, inventory)
        }

        val size = layout.size * 9 - itemMap.size
        val currentPage = if (getCurrentPage() < 0) 0 else getCurrentPage()
        val generatorSource = generatorSource().filter(filter).sortedWith(comparator)
        val generator = generatorSource.drop(currentPage * size).take(size).iterator()

        lastPage.set(ceil(generatorSource.size / size.toFloat()).toInt() - 1)

        val items = ArrayList<ItemStack>(inventory.size)
        val replacements = replacements.mapValues { it.value().toString() }.toMutableMap()

        for (slot in 0..<inventory.size) {
            val item = itemMap[slot]?.clone()
            if (item != null) {
                if (!item.condition(Conditions(this, item, slot))) continue
                item.replace(replacements, updateReplacements)
                items.add(item.item())
            } else {
                if (generator.hasNext()) {
                    val value = generator.next()
                    val itemGen = generatorOutput(value)
                    if (itemGen != null && itemGen.condition(Conditions(this, itemGen, slot))) {
                        itemGen.replace(replacements, updateReplacements)
                        items.add(itemGen.item())
                    }
                } else {
                    items.add(ItemStack(Material.AIR))
                }
            }
        }

        if (!alreadyOpen) {
            player.sendPacket(OpenWindow(windowId, inventory.type(), inventory.title()))
        }

        player.sendPacket(WindowItems(windowId.toShort(), items as List<BaseItemStack>, 1))
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