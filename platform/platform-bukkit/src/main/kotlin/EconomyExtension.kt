package ru.snapix.library

import org.bukkit.entity.Player
import java.util.concurrent.CompletableFuture

val api = snapiLibraryBukkit.economy

fun Player.hasMoney(amount: Int): Boolean {
    return api.getBalance(this) >= amount
}

fun Player.withdrawMoney(amount: Int, success: (Player) -> Unit = {}, fail: (Player) -> Unit = {}) {
    if (hasMoney(amount)) {
        api.withdrawPlayer(this, amount.toDouble())
        success(this)
        return
    }

    fail(this)
}