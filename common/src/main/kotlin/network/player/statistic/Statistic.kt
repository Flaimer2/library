package ru.snapix.library.network.player.statistic

import kotlinx.serialization.Serializable
import ru.snapix.library.Replacement

@Serializable
class Statistic(val username: String, val statistic: Replacement)