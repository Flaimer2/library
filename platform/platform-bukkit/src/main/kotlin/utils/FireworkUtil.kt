package ru.snapix.library.bukkit.utils

import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import fr.mrmicky.fastparticles.ParticleType

fun Location.launchFirework(effect: FireworkEffect, power: Int) {
    val firework = world.spawnEntity(this, EntityType.FIREWORK) as Firework
    val fireworkMeta = firework.fireworkMeta
    fireworkMeta.addEffects(effect)
    fireworkMeta.power = power
    firework.fireworkMeta = fireworkMeta
}

fun Location.particleSpawn(particle: String, amount: Int, speed: Int) {
    val particleType = ParticleType.of(particle)
    particleType.spawn(world, this, amount, 0.0, 0.0, 0.0, speed)
}