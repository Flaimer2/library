package ru.snapix.library.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.snapix.library.network.player.NetworkPlayer
import ru.snapix.library.network.player.OfflineNetworkPlayer
import ru.snapix.library.network.player.OnlineNetworkPlayer

val json = Json {
    serializersModule = SerializersModule {
        polymorphic(NetworkPlayer::class) {
            subclass(OnlineNetworkPlayer::class)
            subclass(OfflineNetworkPlayer::class)
        }
        ignoreUnknownKeys = true
    }
}