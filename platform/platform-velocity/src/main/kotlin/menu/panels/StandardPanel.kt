package ru.snapix.library.menu.panels

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.scheduler.ScheduledTask
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.data.inventory.InventoryType
import ru.snapix.library.menu.*
import ru.snapix.library.repeatTask
import ru.snapix.library.snapiLibrary
import kotlin.time.Duration

class StandardPanel internal constructor(
    player: Player,
    title: String,
    update: Duration?,
    replacements: List<Replacement>,
    layout: Layout,
    items: List<Item>
) : VelocityPanel(player, title, update, replacements) {
    override val inventory: Inventory
    override val updateTimer: ScheduledTask?
    private val itemMap = emptyItemMap()

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

        Protocolize.playerProvider().player(player.uniqueId).openInventory(inventory)
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

    override fun getItemBySlot(slot: Int): Item? {
        return itemMap[slot]
    }

    override fun onOpen() {}

    override fun onClose() {
        disable()
    }
}