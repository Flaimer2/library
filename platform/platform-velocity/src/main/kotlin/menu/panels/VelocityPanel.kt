package ru.snapix.library.menu.panels

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.scheduler.ScheduledTask
import dev.simplix.protocolize.api.ClickType
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.data.packets.SetSlot
import ru.snapix.library.menu.*
import kotlin.time.Duration


abstract class VelocityPanel internal constructor(
    val player: Player,
    override val title: String,
    override val update: Duration?,
    override val replacements: List<Replacement>
) : Panel {
    abstract val inventory: Inventory
    abstract val updateTimer: ScheduledTask?

    open fun render(itemMap: ItemMap) {
        val player = Protocolize.playerProvider().player(player.uniqueId) ?: return

        var windowId = -1

        for (id in player.registeredInventories().keys) {
            val value = player.registeredInventories()[id]
            if (value == inventory) {
                windowId = id
                break
            }
        }
        if (windowId == -1) return

        var title = title
        for (replacement in replacements) {
            title = title.replace("{${replacement.first}}", replacement.second().toString())
        }

        for (slot in 0..<inventory.size) {
            val item = itemMap[slot]?.clone()
            if (item != null && item.condition(Conditions(this, item, slot))) {
                item.replace(replacements)
                inventory.item(slot, item.item())
                player.sendPacket(SetSlot(windowId.toByte(), slot.toShort(), item.item(), 1))
            } else if (inventory.item(slot) != null) {
                inventory.removeItem(slot)
                player.sendPacket(SetSlot(windowId.toByte(), slot.toShort(), null, 1))
            }
        }
    }

    override fun disable() {
        updateTimer?.cancel()
    }

    fun runClickCallbacks(slot: Int, type: ClickType) {
        val item = getItemBySlot(slot) ?: return
        val clickAction = item.clickAction ?: return

        val click = Click(player, this, type, item, slot)

        click.clickAction()
    }

    abstract fun getItemBySlot(slot: Int): Item?
}