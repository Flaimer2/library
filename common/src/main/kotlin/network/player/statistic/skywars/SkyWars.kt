package ru.snapix.profile.statistics.skywars

import com.google.gson.JsonParser
import kotlinx.serialization.json.Json
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.player.statistic.bedwars.BedWarsDTO
import ru.snapix.library.utils.executeQuery
import ru.snapix.library.utils.replaceLast

object SkyWars {
    internal fun get(name: String): Replacement {
        val replacement = Replacement()
        var result: SkyWarsDTO? = null

        SnapiLibrary.globalDatabase.executeQuery("SELECT `Data` FROM server_minigames.`ultraskywars` WHERE `Name` = '$name'") {
            result = Json.decodeFromString<SkyWarsDTO>(it.getString("Data"))
        }

        for (type in SkyWarsType.entries) {
            replacement["skywars_${type.name.lowercase()}"] = { result?.getStatistic(type)?.toString() ?: "0" }
        }

        return replacement
    }
}
