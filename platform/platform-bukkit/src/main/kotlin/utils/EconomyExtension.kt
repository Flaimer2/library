package ru.snapix.library.bukkit.utils

import org.bukkit.entity.Player
import ru.snapix.library.bukkit.plugin

val api = plugin.economy

fun Player.hasMoney(amount: Int): Boolean {
    return api.getBalance(this) >= amount
}

fun Player.getMoney(): Int {
    return api.getBalance(this).toInt()
}

fun Player.withdrawMoney(amount: Int, success: (Player) -> Unit = {}, fail: (Player) -> Unit = {}) {
    if (hasMoney(amount)) {
        api.withdrawPlayer(this, amount.toDouble())
        success(this)
        return
    }

    fail(this)
}

fun Player.depositMoney(amount: Int) {
    api.depositPlayer(this, amount.toDouble())
}