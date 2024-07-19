package ru.snapix.library.utils

import com.cryptomorin.xseries.XMaterial
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.lang.reflect.Field
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
        } else {
            runBlocking {
                async {
                    val profile = JsonParser().parse(fromURL("${mojangAPI[0]}$name")) as? JsonObject
                    if (profile != null) {
                        val uuid = profile["id"].asString
                        (JsonParser().parse(fromURL("${mojangAPI[1]}$uuid")) as JsonObject)
                            .getAsJsonArray("properties")
                            .any {
                                if ("textures" == it.asJsonObject["name"].asString) {
                                    result = requestCustomTextureHead(it.asJsonObject["value"].asString).also { head ->
                                        cachedSkulls[name] = head
                                    }
                                    true
                                } else false
                            }
                    }
                }.await()
            }
        }
    }
    return result ?: defaultHead
}

fun ItemStack.modifyHeadTexture(input: String): ItemStack {
    val profile = GameProfile(UUID.randomUUID(), null)
    val texture = if (input.length in 60..100)
        Base64.getEncoder()
            .encodeToString("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/$input\"}}}".toByteArray())
    else input
    profile.properties.put("textures", Property("textures", texture, "SnapiX_TexturedSkull"))

    val meta = itemMeta as SkullMeta
    val profileField: Field?
    try {
        profileField = meta.javaClass.getDeclaredField("profile")
        profileField.setAccessible(true)
        profileField.set(meta, profile)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }

    itemMeta = meta

    return this
}
