package ru.snapix.library

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player


object PlayerSerializer : KSerializer<Player> {
    override val descriptor = PrimitiveSerialDescriptor("Player", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = Bukkit.getPlayer(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: Player) = encoder.encodeString(value.name)
}

object LocationSerializer : KSerializer<Location> {
    override val descriptor = PrimitiveSerialDescriptor("Location", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): Location {
        val parts = decoder.decodeString().split(";")
        if (parts.size != 6) return Location(null, 0.0, 0.0, 0.0)
        val world = Bukkit.getServer().getWorld(parts[3]) ?: return Location(null, 0.0, 0.0, 0.0)
        val x = parts[0].toDouble()
        val y = parts[1].toDouble()
        val z = parts[2].toDouble()
        val yaw = parts[4].toFloat()
        val pitch = parts[5].toFloat()

        return Location(world, x, y, z, yaw, pitch)
    }

    override fun serialize(encoder: Encoder, value: Location) =
        encoder.encodeString("${value.x};${value.y};${value.z};${value.world.name};${value.yaw};${value.pitch}")
}
