package ru.snapix.library.network.player.statistic.bedwars

import com.google.gson.JsonParser
import kotlinx.serialization.json.Json
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.utils.executeQuery
import ru.snapix.library.utils.fieldNames
import ru.snapix.library.utils.json
import ru.snapix.library.utils.replaceLast

object BedWars {
    internal fun get(name: String): MutableMap<String, String> {
        val replacement = mutableMapOf<String, String>()
        var result: BedWarsDTO? = null
        SnapiLibrary.globalDatabase.executeQuery("SELECT `data` FROM server_minigames.`bedwars` WHERE `name` = '$name'") {
            val jsonObject = JsonParser().parse(it.getString("data")).asJsonObject
            val obj = jsonObject.get("totalGroups").toString()
                .replaceFirst("\"", "")
                .replaceLast("\"", "")
                .replace("\\\\", "")
                .replace("\\", "")
            val dto = json.decodeFromString<BedWarsDTO>(obj)
            result = dto
        }

        for (type in fieldNames(BedWarsDTO::class)) {
            if (type == "Companion") continue
            val field = BedWarsDTO::class.java.getDeclaredField(type)
            field.isAccessible = true
            val value = try {
                field.getInt(result)
            } catch (e: Exception) {
                0
            }
            replacement["bedwars_${type.lowercase()}"] = value.toString()
        }

        return replacement
    }
}