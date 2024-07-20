package ru.snapix.library.menu.dsl

import org.bukkit.entity.Player
import ru.snapix.library.menu.Item
import ru.snapix.library.menu.Replacement
import ru.snapix.library.menu.panels.StandardPanel
import kotlin.time.Duration

fun panel(player: Player, builder: PanelBuilder.() -> Unit): StandardPanel {
    return PanelBuilder(player).apply(builder).build()
}

class PanelBuilder(val player: Player) {
    var title: String? = null
    val items = mutableListOf<Item>()
    val layout = mutableListOf<String>()
    val replacements = mutableListOf<Replacement>()
    var update: Duration? = null
    var updateReplacements: (String) -> String = { it }

    fun PanelBuilder.layout(setup: LayoutBuilder.() -> Unit) {
        layout.addAll(LayoutBuilder().apply(setup).list)
    }

    fun PanelBuilder.items(setup: ItemsBuilder.() -> Unit) {
        items.addAll(ItemsBuilder().apply(setup).items)
    }

    fun PanelBuilder.replacements(setup: ReplacementsBuilder.() -> Unit) {
        replacements.addAll(ReplacementsBuilder().apply(setup).list)
    }

    fun build(): StandardPanel {
        return StandardPanel(player, title ?: "Kotlin Standard Panel", update, replacements, layout, items, updateReplacements)
    }
}