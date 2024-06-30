package ru.snapix.library.menu

import com.velocitypowered.api.proxy.Player
import dev.simplix.protocolize.api.item.ItemFlag
import kotlin.time.Duration

fun menu(player: Player? = null, builder: MenuBuilder.() -> Unit): VelocityInventory {
    return MenuBuilder(player).apply(builder).build()
}

class MenuBuilder(val player: Player?) {
    var title: String? = null
    val items = mutableListOf<Item>()
    val layout = mutableListOf<String>()
    val replacements = mutableListOf<Pair<String, () -> Any>>()
    var update: Duration? = null

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

    fun build(): VelocityInventory {
        return VelocityInventory(title ?: "DefaultSnapTitle", items, layout, replacements, update)
    }
}

class ItemsBuilder {
    val list = mutableListOf<Item>()

    operator fun Char.invoke(setup: ItemBuilder.() -> Unit) {
        list.add(ItemBuilder(this).apply(setup).build())
    }

    class ItemBuilder(val index: Char) {
        var name: String? = null
        var material: Material = Material.AIR
        var amount: Int = 1
        var lore = mutableListOf<String>()
        var itemFlag = mutableListOf<ItemFlag>()
        var actions: ClickAction? = null

        fun lore(setup: LoreBuilder.() -> Unit) {
            lore = LoreBuilder().apply(setup).list
        }

        fun itemFlag(setup: ItemFlagBuilder.() -> Unit) {
            itemFlag = ItemFlagBuilder().apply(setup).list
        }

        fun actions(actions: ClickAction) {
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
            return Item(index, name, material, amount, lore, itemFlag, actions)
        }
    }
}
