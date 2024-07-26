package ru.snapix.library.network.player.statistic

import kotlin.enums.EnumEntries

enum class DatabasePlugin(val tableName: String, val nameField: String, val column: EnumEntries<*>) {
    MINERWARE("server_minigames.minerware_stats", "NAME", MinerWareType.entries),
    MURDERMYSTERY("server_minigames.murdermystery_stats", "name", MurderMysteryType.entries),
    THEBRIDGE("server_minigames.thebridge_stats", "name", TheBridgeType.entries),
    ALONSOLEVELS("server_global.account_levels", "playername", AlonsoLevelsType.entries)
}

enum class MinerWareType {
    WINS,
    GAMES_PLAYED,
    MAX_POINTS
}

enum class MurderMysteryType {
    WINS,
    GAMES_PLAYED,
    LOSES,
    KILLS,
    DEATHS,
    CONTRIBUTION_MURDERER,
    CONTRIBUTION_DETECTIVE,
    PASS_MURDERER,
    PASS_DETECTIVE,
    HIGHEST_SCORE
}

enum class TheBridgeType {
    WINS,
    LOSES,
    GAMESPLAYED,
    KILLS,
    DEATHS,
    POINTS
}

enum class AlonsoLevelsType {
    EXPERIENCE,
    LASTLEVEL,
}
