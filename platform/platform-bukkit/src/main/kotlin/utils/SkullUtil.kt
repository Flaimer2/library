package ru.snapix.library.bukkit.utils

import com.cryptomorin.xseries.XMaterial
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import ru.snapix.library.utils.fromURL
import ru.snapix.library.utils.getPlayerTexture
import java.util.*
import java.util.concurrent.ConcurrentHashMap


private val mojangAPI = arrayOf(
    "https://api.mojang.com/users/profiles/minecraft/",
    "https://sessionserver.mojang.com/session/minecraft/profile/"
)
private val defaultHead = XMaterial.PLAYER_HEAD.parseItem()!!
private val cachedSkulls = ConcurrentHashMap<String, ItemStack>()

fun requestHead(identifier: String): ItemStack {
    return if (identifier.length > 30)
        requestCustomTextureHead(identifier)
    else
        requestPlayerHead(identifier)
}

private fun requestCustomTextureHead(texture: String) =
    cachedSkulls
        .computeIfAbsent(texture) {
            defaultHead.clone().modifyHeadTexture(texture)
        }
        .clone()

private fun requestPlayerHead(name: String): ItemStack {
    var result: ItemStack? = null

    if (name in cachedSkulls.keys) {
        result = cachedSkulls[name]
    } else {
        val playerTexture = getPlayerTexture(name)
        if (playerTexture != null) {
            result = requestCustomTextureHead(playerTexture)
        }
    }
    return result ?: defaultHead.clone()
}

fun ItemStack.modifyHeadTexture(input: String): ItemStack {
    val hashAsId = UUID(input.hashCode().toLong(), input.hashCode().toLong())
    val texture = if (input.length in 60..100)
        Base64.getEncoder()
            .encodeToString("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/$input\"}}}".toByteArray())
    else input
    return Bukkit.getUnsafe().modifyItemStack(
        this,
        "{SkullOwner:{Id:\"$hashAsId\",Properties:{textures:[{Value:\"$texture\"}]}}}"
    )
//    val profile = GameProfile(UUID.randomUUID(), null)
//    val texture = if (input.length in 60..100)
//        Base64.getEncoder()
//            .encodeToString("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/$input\"}}}".toByteArray())
//    else input
//    profile.properties.put("textures", Property("textures", texture, "SnapiX_TexturedSkull"))
//
//    val meta = itemMeta as SkullMeta
//    val profileField: Field?
//    try {
//        profileField = meta.javaClass.getDeclaredField("profile")
//        profileField.setAccessible(true)
//        profileField.set(meta, profile)
//    } catch (e: NoSuchFieldException) {
//        e.printStackTrace()
//    } catch (e: IllegalArgumentException) {
//        e.printStackTrace()
//    } catch (e: IllegalAccessException) {
//        e.printStackTrace()
//    }
//
//    itemMeta = meta
//
//    return this
}