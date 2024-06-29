package ru.snapix.library.menu

import com.cryptomorin.xseries.XMaterial
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import ru.snapix.library.players
import java.util.function.Supplier

fun menu(player: Player? = null, builder: MenuBuilder.() -> Unit): BukkitInventory {
    return MenuBuilder(player).apply(builder).build()
}

class MenuBuilder(val player: Player?) {
    val layout = mutableListOf<String>()
    val items = mutableListOf<Item>()
    val replacements = mutableListOf<Pair<String, () -> Any>>()
    var title: String? = null
    var update: Int = -1

    fun MenuBuilder.layout(setup: LayoutBuilder.() -> Unit) {
        layout.addAll(LayoutBuilder().apply(setup).list)
    }

    fun MenuBuilder.items(setup: ItemsBuilder.() -> Unit) {
        items.addAll(ItemsBuilder().apply(setup).list)
    }

    fun MenuBuilder.replacements(setup: ReplacementsBuilder.() -> Unit) {
        replacements.addAll(ReplacementsBuilder().apply(setup).list)
    }

    class LayoutBuilder {
        val list = mutableListOf<String>()

        operator fun String.unaryMinus() {
            list.add(this)
        }
    }

    class ReplacementsBuilder {
        val list = mutableListOf<Pair<String, () -> Any>>()

        operator fun Pair<String, () -> Any>.unaryMinus() {
            list.add(this)
        }
    }

    fun build(): BukkitInventory {
        return BukkitInventory(title ?: "DefaultSnapTitle", layout, items, replacements, update)
    }
}

class ItemsBuilder {
    val list = mutableListOf<Item>()

    operator fun Char.invoke(setup: ItemBuilder.() -> Unit) {
        list.add(ItemBuilder(this).apply(setup).build())
    }

    class ItemBuilder(val index: Char) {
        var name: String? = null
        var material: XMaterial = XMaterial.AIR
        var amount: Int = 1
        var lore = mutableListOf<String>()
        var itemFlag = mutableListOf<ItemFlag>()
        var actions: (ClickAction.() -> Unit)? = null

        fun lore(setup: LoreBuilder.() -> Unit) {
            lore = LoreBuilder().apply(setup).list
        }

        fun itemFlag(setup: ItemFlagBuilder.() -> Unit) {
            itemFlag = ItemFlagBuilder().apply(setup).list
        }

        fun actions(actions: ClickAction.() -> Unit) {
            this.actions = actions
        }

        class LoreBuilder {
            val list = mutableListOf<String>()

            operator fun String.unaryMinus() {
                list.add(this)
            }
        }

        class ItemFlagBuilder {
            val list = mutableListOf<ItemFlag>()

            operator fun ItemFlag.unaryMinus() {
                list.add(this)
            }
        }

        fun build(): Item {
            return Item(index, name, material, amount, lore, clickAction = actions)
        }
    }
}
