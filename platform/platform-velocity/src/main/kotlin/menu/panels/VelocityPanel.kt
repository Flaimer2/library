package ru.snapix.library.menu.panels

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.scheduler.ScheduledTask
import dev.simplix.protocolize.api.ClickType
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.api.item.BaseItemStack
import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.packets.OpenWindow
import dev.simplix.protocolize.data.packets.WindowItems
import ru.snapix.library.menu.*
import ru.snapix.library.menu.dsl.Material
import kotlin.time.Duration
import kotlin.time.measureTime


abstract class VelocityPanel internal constructor(
    val player: Player,
    override val title: String,
    override val update: Duration?,
    override val replacements: Replacement,
    override val updateReplacements: (String) -> String
) : Panel {
    abstract val inventory: Inventory
    abstract val updateTimer: ScheduledTask?

    open fun render(itemMap: ItemMap) {
        println(measureTime {
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

            val items = ArrayList<ItemStack>(inventory.size)
            val replacements = replacements.mapValues { it.value().toString() }.toMutableMap()

            for (slot in 0..<inventory.size) {
                val item = itemMap[slot]?.clone()
                if (item != null && item.condition(Conditions(this@VelocityPanel, item, slot))) {
                    item.replace(replacements, updateReplacements)
                    items.add(item.item())
                } else {
                    items.add(ItemStack.NO_DATA)
                }
            }

            if (!alreadyOpen) {
                player.sendPacket(OpenWindow(windowId, inventory.type(), inventory.title()))
            }

            player.sendPacket(WindowItems(windowId.toShort(), items as List<BaseItemStack>, 1))
        })
    }

    override fun disable() {
        updateTimer?.cancel()
    }

    fun runClickCallbacks(slot: Int, type: ClickType) {
        val item = getItemBySlot(slot)
        val clickAction = item?.clickAction

        if (clickAction != null) {
            val click = Click(player, this, type, item, slot)
            click.clickAction()
        }
    }

    abstract fun getItemBySlot(slot: Int): Item?
}