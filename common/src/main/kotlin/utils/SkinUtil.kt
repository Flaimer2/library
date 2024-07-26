package ru.snapix.library.utils

import ru.snapix.library.SnapiLibrary

fun getPlayerTexture(name: String): String? {
    return SnapiLibrary.globalDatabase.executeQuery("SELECT * FROM `lastloginapi_players` left join `skinrestorer_players` on lastloginapi_players.uuid = skinrestorer_players.uuid left join `skinrestorer_player_skins` on skin_identifier = skinrestorer_player_skins.uuid where name = '$name'") {
        it.getString("value")
    }.firstOrNull()
}