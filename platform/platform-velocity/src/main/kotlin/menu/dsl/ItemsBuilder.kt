package ru.snapix.library.menu.dsl

import dev.simplix.protocolize.api.item.ItemFlag
import dev.simplix.protocolize.data.ItemType
import ru.snapix.library.menu.Item
import ru.snapix.library.menu.ClickAction

class ItemsBuilder {
    val items = mutableListOf<Item>()

    operator fun Char.invoke(setup: ItemBuilder.() -> Unit) {
        items.add(ItemBuilder(this).apply(setup).build())
    }
}

class ItemBuilder(val index: Char? = null) {
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

typealias Material = ItemType