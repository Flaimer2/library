package ru.snapix.library.bukkit.panel

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent

class InventoryListener : Listener {
    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val holder = event.clickedInventory?.holder
        if (holder !is BukkitPanel) return

        event.isCancelled = true

        if (event.currentItem == null) return

        holder.runClickCallbacks(event.slot, event.click)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val holder = event.inventory?.holder
        if (holder !is BukkitPanel) return

        holder.onClose()
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        event.player.closeInventory()
    }
}