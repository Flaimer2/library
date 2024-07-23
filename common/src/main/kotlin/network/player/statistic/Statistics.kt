package ru.snapix.library.network.player.statistic

import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.player.statistic.bedwars.BedWars
import ru.snapix.library.utils.executeQuery
import ru.snapix.library.utils.toShortTime
import ru.snapix.profile.statistics.skywars.SkyWars

object Statistics {
    fun getReplacements(username: String): Replacement {
        return (StatisticCache[username] ?: update(username)).statistic
    }

    fun update(username: String): Statistic {
        val replacements = Replacement()

        replacements.putAll(SkyWars.get(username))
        replacements.putAll(BedWars.get(username))

        val statistic = Statistic(username, replacements)
        StatisticCache.update(statistic)

        return statistic
    }

    private fun getDatabaseReplacement(username: String): Replacement {
        val replacement = Replacement()
        for (plugin in DatabasePlugin.entries) {
            val value: ResultRow = get(username, plugin)

            for (type in plugin.column) {
                if (plugin.name == "PARKOUR" && type.name == "TIME") {
                    replacement["${plugin.name}_${type.name}"] = {
                        value[type.name]?.toLong()?.toShortTime("mm мин. ss сек.") ?: "&cНет"
                    }
                }
                replacement["${plugin.name}_${type.name}"] = { value[type.name]?.toString() ?: "0" }
            }
        }
        return replacement
    }

    private fun get(username: String, databasePlugin: DatabasePlugin): ResultRow {
        val map: ResultRow = rowOf()

        SnapiLibrary.globalDatabase.executeQuery("SELECT * FROM ${databasePlugin.tableName} WHERE ${databasePlugin.nameField} = '$username'") {
            for (type in databasePlugin.column) {
                map[type.name] = it.getInt(type.name)
            }
        }

        return map
    }
}

typealias ResultRow = MutableMap<String, Int>
fun rowOf(vararg pairs: Pair<String, Int>): ResultRow {
    return mutableMapOf(*pairs)
}
