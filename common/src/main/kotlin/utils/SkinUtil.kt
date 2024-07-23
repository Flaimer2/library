package ru.snapix.library.utils

import ru.snapix.library.SnapiLibrary

fun getPlayerTexture(name: String): String? {
    return SnapiLibrary.globalDatabase.executeQuery("SELECT * FROM `skinrestorer_player_skins` WHERE `last_known_name` = '$name'") {
        it.getString("value")
    }.firstOrNull()
}