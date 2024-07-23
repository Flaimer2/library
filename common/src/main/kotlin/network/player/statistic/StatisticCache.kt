package ru.snapix.library.network.player.statistic

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.snapix.library.cache.RedisCache

object StatisticCache : RedisCache<Statistic>() {
    override val KEY_REDIS: String = "statistic"
    override fun key(value: Statistic) = value.username
    override fun encode(value: Statistic) = Json.encodeToString(value)
    override fun decode(value: String) = Json.decodeFromString<Statistic>(value)
}