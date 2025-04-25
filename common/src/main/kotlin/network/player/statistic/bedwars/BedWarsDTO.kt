package ru.snapix.library.network.player.statistic.bedwars

import kotlinx.serialization.Serializable

@Serializable
data class BedWarsDTO(
    val deaths: Int,
    val kills: Int,
    val wins: Int,
    val winSteak: Int,
    val emeraldCollected: Int,
    val ironCollected: Int,
    val blockBreak: Int,
    val finalDeaths: Int,
    val projectileShoot: Int,
    val diamondCollected: Int,
    val blockPlaced: Int,
    val tntPlaced: Int,
    val finalKills: Int,
    val bedsLost: Int,
    val gamesPlayed: Int,
    val goldCollected: Int,
    val fireballShoot: Int,
    val bedsBroken: Int,
    val projectileHit: Int
)