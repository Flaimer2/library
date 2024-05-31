package ru.snapix.library

enum class ServerType(val compactName: String) {
    AUTH("AUTH"),
    LOBBY("L"),
    CLASSIC("C"),
    SKYBLOCK("SB"),
    SKYPVP("SP"),
    ANARCHY("A"),
    SKYWARS("SW"),
    BEDWARS("BW"),
    MURDERMYSTERY("MM"),
    THEBRIDGE("TB"),
    MINERWARE("MW"),
    BUILDBATTLE("BB"),
    UNKNOWN("UNK");

    fun isSurvival() = this == CLASSIC || this == SKYBLOCK || this == SKYPVP || this == ANARCHY
    fun isGame() =
        this == SKYWARS || this == BEDWARS || this == MURDERMYSTERY || this == THEBRIDGE || this == MINERWARE || this == BUILDBATTLE

    companion object {
        operator fun invoke(server: String): ServerType {
            var result = UNKNOWN

            for (mode in entries) {
                if (server.startsWith(mode.name, ignoreCase = true) || server.startsWith(mode.compactName, ignoreCase = true)) {
                    result = mode
                    break
                }
            }

            return result
        }
    }
}