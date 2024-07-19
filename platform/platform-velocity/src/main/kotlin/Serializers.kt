package ru.snapix.library

import com.velocitypowered.proxy.connection.client.ConnectedPlayer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.jvm.optionals.getOrNull

object ConnectedPlayerSerializer : KSerializer<ConnectedPlayer> {
    override val descriptor = PrimitiveSerialDescriptor("ConnectedPlayer", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): ConnectedPlayer =
        snapiLibrary.server.getPlayer(decoder.decodeString()).getOrNull() as ConnectedPlayer?
            ?: error("Not found player")

    override fun serialize(encoder: Encoder, value: ConnectedPlayer) = encoder.encodeString(value.username)
}

val json = Json {
    serializersModule = SerializersModule {
        contextual(ConnectedPlayerSerializer)
    }
    encodeDefaults = true
    ignoreUnknownKeys = true
}