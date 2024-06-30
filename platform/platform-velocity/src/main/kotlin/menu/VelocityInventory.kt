package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.ClickType
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.Inventory
import dev.simplix.protocolize.data.inventory.InventoryType
import kotlinx.coroutines.channels.ReceiveChannel
import ru.snapix.library.runTaskTimer
import ru.snapix.library.snapiLibrary
import kotlin.jvm.optionals.getOrNull
import kotlin.time.Duration

class VelocityInventory {
    private lateinit var player: Player
    private val inventory: Inventory
    private val itemMap = emptyItemMap()
    private val replacements: List<Replacement>
    private val updateTimer: ReceiveChannel<Unit>?

    constructor(title: String = "DefaultSnapTitle", items: List<Item> = emptyList(), layout: List<String> = emptyList(), replacements: List<Replacement> = emptyList(), update: Duration? = null) {
        inventory = Inventory(InventoryType.chestInventoryWithRows(layout.size * 9))
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
        inventory.onClose {
            val player = snapiLibrary.server.getPlayer(it.player().uniqueId()).getOrNull() ?: return@onClose
            onClose(player)
        }
        inventory.onClick {
            val player = snapiLibrary.server.getPlayer(it.player().uniqueId()).getOrNull() ?: return@onClick
            click(player, it.slot(), it.clickType())
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
        this.player = player
        update()
    }

    internal fun onClose(player: Player) {
        disable()
    }

    internal fun click(player: Player, slot: Int, type: ClickType) {
        val item = itemMap[slot] ?: return
        val clickAction = item.clickAction ?: return

        val click = Click(player, type, item, slot)

        click.clickAction()
    }

    fun update() {
        val items = itemMap.mapValues { it.value.clone() }
        for ((slot, item) in items) {
            for (replace in replacements) {
                item.name = item.name?.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true)
                item.lore =
                    item.lore.map { it.replace("{${replace.first}}", replace.second().toString(), ignoreCase = true) }
            }
            inventory.item(slot, item.item())
        }
    }

    fun openForPlayer(player: Player) {
        onOpen(player)
        Protocolize.playerProvider().player(player.uniqueId).openInventory(inventory)
    }
}