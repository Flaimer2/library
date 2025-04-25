package ru.snapix.library.network

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
    UNKNOWN("UNK");

    fun isSurvival() = this == CLASSIC || this == SKYBLOCK || this == SKYPVP || this == ANARCHY
    fun isGame() =
        this == SKYWARS || this == BEDWARS || this == MURDERMYSTERY || this == THEBRIDGE

    companion object {
        @JvmStatic
        operator fun get(server: String): ServerType {
            var result = UNKNOWN

            for (type in entries) {
                if (server.startsWith(type.name, ignoreCase = true) || server.startsWith(
                        type.compactName,
                        ignoreCase = true
                    )
                ) {
                    result = type
                    break
                }
            }

            return result
        }
    }
}