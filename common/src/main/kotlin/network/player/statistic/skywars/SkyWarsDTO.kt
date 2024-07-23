package ru.snapix.profile.statistics.skywars

import kotlinx.serialization.Serializable

@Serializable
data class SkyWarsDTO(val totalStats: Map<String, Int>) {
    fun getStatistic(type: SkyWarsType): Int {
        return totalStats[type.name] ?: 0
    }
}