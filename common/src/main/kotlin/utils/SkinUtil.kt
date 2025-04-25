package ru.snapix.library.utils

import com.alessiodp.lastloginapi.api.LastLogin
import com.alessiodp.lastloginapi.api.interfaces.LastLoginAPI
import com.velocitypowered.api.proxy.Player
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.snapix.library.SnapiLibrary
import ru.snapix.library.cache.DatabaseCache
import ru.snapix.library.cache.RedisCache

fun getPlayerTexture(name: String): String? {
    return SkinCache[name]?.value
}

@Serializable
data class PlayerSkin(val username: String, val value: String)

object Skins {
    fun load() {
        SkinDatabase.load()
    }

    fun update() {
        SkinDatabase.values().forEach { SkinCache.update(it) }
    }

    fun update(name: String, value: String) {
        val playerSkin = PlayerSkin(name, value)
        if (SkinCache[name] != null) {
            SkinDatabase.update(playerSkin)
            SkinCache.update(playerSkin)
        } else {
            SkinDatabase.create(playerSkin)
            SkinCache.update(playerSkin)
        }
    }

    fun update(player: Player) {
        val value = player.gameProfileProperties.firstOrNull { it.name == "textures" }?.value ?: return
        update(player.username, value)
    }
}

object SkinCache : DatabaseCache<PlayerSkin>() {
    override val KEY_REDIS = "player-skin"
    override fun decode(value: String) = json.decodeFromString<PlayerSkin>(value)
    override fun encode(value: PlayerSkin) = json.encodeToString(value)
    override fun key(value: PlayerSkin) = value.username.lowercase()
    override fun valueFromDatabase(key: String) = SkinDatabase[key]
}

object SkinTable : Table("player_skins") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 32).uniqueIndex()
    val value: Column<String> = text("value")

    override val primaryKey = PrimaryKey(id)
}

internal object SkinDatabase {
    fun load() {
        transaction(SnapiLibrary.globalDatabase) {
            SchemaUtils.create(SkinTable)
        }
    }
    fun create(skin: PlayerSkin) {
        transaction(SnapiLibrary.globalDatabase) {
            SkinTable.insertIgnore {
                it[name] = skin.username
                it[value] = skin.value
            }
        }
    }

    operator fun get(name: String): PlayerSkin? {
        return transaction(SnapiLibrary.globalDatabase) {
            SkinTable.selectAll().where { SkinTable.name eq name }.map(::toPlayerSkin)
        }.firstOrNull()
    }

    fun update(skin: PlayerSkin) {
        transaction(SnapiLibrary.globalDatabase) {
            SkinTable.update({ SkinTable.name eq skin.username }) {
                it[value] = skin.value
            }
        }
    }

    fun values(): List<PlayerSkin> {
        return transaction(SnapiLibrary.globalDatabase) {
            SkinTable.selectAll().map(::toPlayerSkin)
        }
    }

    private fun toPlayerSkin(resultRow: ResultRow): PlayerSkin {
        return PlayerSkin(resultRow[SkinTable.name], resultRow[SkinTable.value])
    }
}