package ru.snapix.library.menu

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent


class InventoryListener : Listener {
    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val inventory = event.clickedInventory
        if (inventory != null && inventory.holder is BukkitInventory) {
            event.isCancelled = true

            if (event.currentItem != null) {
                (inventory.holder as BukkitInventory).executeClickAction(ClickAction(event.whoClicked as Player, event.click, event.currentItem, event.slot))
            }
        }
    }
}