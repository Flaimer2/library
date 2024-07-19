package ru.snapix.library.utils

import dev.simplix.protocolize.api.item.ItemStack
import dev.simplix.protocolize.data.ItemType
import net.querz.nbt.tag.CompoundTag
import net.querz.nbt.tag.ListTag
import net.querz.nbt.tag.StringTag
import java.util.*

fun requestHead(identifier: String): ItemStack {
    return if (identifier.length > 30)
        requestCustomTextureHead(identifier)
    else
        requestPlayerHead(identifier)
}

private fun requestCustomTextureHead(texture: String) = ItemStack(ItemType.PLAYER_HEAD).modifyHeadTexture(texture)

private fun requestPlayerHead(name: String): ItemStack {
    return requestCustomTextureHead(getPlayerTexture(name) ?: name)
}

fun ItemStack.modifyHeadTexture(input: String): ItemStack {
    val tag = nbtData()

    if (input.length < 30) {
        tag.put("SkullOwner", StringTag(input))

        nbtData(tag)

        return this
    }

    val texture = if (input.length in 60..100)
        Base64.getEncoder()
            .encodeToString("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/$input\"}}}".toByteArray())
    else input
    val skullOwnerTag = tag.getCompoundTag("SkullOwner") ?: CompoundTag()
    val propertiesTag = tag.getCompoundTag("Properties") ?: CompoundTag()
    val texturesTag = ListTag(CompoundTag::class.java)
    val textureTag = CompoundTag()

    textureTag.put("Value", StringTag(texture))
    texturesTag.add(textureTag)
    propertiesTag.put("textures", texturesTag)
    skullOwnerTag.put("Properties", propertiesTag)
    skullOwnerTag.put("Name", StringTag(texture))

    tag.put("SkullOwner", skullOwnerTag)

    nbtData(tag)

    return this
}
