package ru.snapix.library

import org.bukkit.entity.Player

val api = snapiLibrary.economy

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