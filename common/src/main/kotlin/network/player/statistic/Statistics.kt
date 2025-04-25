package ru.snapix.library.network.player.statistic

import kotlinx.serialization.Contextual
import ru.snapix.library.Replacement
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.network.player.statistic.bedwars.BedWars
import ru.snapix.library.network.player.statistic.skywars.SkyWars
import ru.snapix.library.utils.executeQuery
import ru.snapix.library.utils.toShortTime

object Statistics {
    fun getReplacements(username: String): Replacement {
        val replacement = Replacement()
        val value = StatisticCache[username]?.statistic ?: update(username).statistic
        replacement.putAll(value.mapValues { { it.value } })
        return replacement
    }

    fun update(username: String): Statistic {
        val replacements = mutableMapOf<String, String>()

        replacements.putAll(SkyWars.get(username))
        replacements.putAll(BedWars.get(username))
        replacements.putAll(getDatabaseReplacement(username))

        val statistic = Statistic(username, replacements)
        StatisticCache.update(statistic)

        return statistic
    }

    private fun getDatabaseReplacement(username: String): MutableMap<String, String> {
        val replacement = mutableMapOf<String, String>()
        for (plugin in DatabasePlugin.entries) {
            val value: ResultRow = get(username, plugin)

            for (type in plugin.column) {
                replacement["${plugin.name}_${type.name}"] = value[type.name]?.toString() ?: "0"
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
