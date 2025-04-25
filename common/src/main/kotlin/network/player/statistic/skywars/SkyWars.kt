package ru.snapix.library.network.player.statistic.skywars

import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.utils.executeQuery
import ru.snapix.library.utils.json

object SkyWars {
    internal fun get(name: String): MutableMap<String, String> {
        val replacement = mutableMapOf<String, String>()
        var result: SkyWarsDTO? = null

        SnapiLibrary.globalDatabase.executeQuery("SELECT `Data` FROM server_minigames.`ultraskywars` WHERE `Name` = '$name'") {
            result = json.decodeFromString<SkyWarsDTO>(it.getString("Data"))
        }

        for (type in SkyWarsType.entries) {
            replacement["skywars_${type.name.lowercase()}"] = result?.getStatistic(type)?.toString() ?: "0"
        }

        return replacement
    }
}
