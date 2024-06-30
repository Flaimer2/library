package ru.snapix.library.menu

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryListener : Listener {
    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val holder = event.clickedInventory?.holder
        if (holder !is BukkitInventory) return

        event.isCancelled = true

        if (event.currentItem == null) return

        val player = event.whoClicked
        if (player !is Player) return

        holder.click(player, event.slot, event.click)
    }

    @EventHandler
    fun onOpen(event: InventoryOpenEvent) {
        val holder = event.inventory?.holder
        if (holder !is BukkitInventory) return

        val player = event.player
        if (player !is Player) return

        holder.onOpen(player)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val holder = event.inventory?.holder
        if (holder !is BukkitInventory) return

        val player = event.player
        if (player !is Player) return

        holder.onClose(player)
    }
}