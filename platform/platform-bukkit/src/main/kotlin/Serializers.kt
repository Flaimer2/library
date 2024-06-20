package ru.snapix.library

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlayerSerializer : KSerializer<Player> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = Bukkit.getPlayer(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: Player) = encoder.encodeString(value.name)
}
