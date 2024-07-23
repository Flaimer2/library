package ru.snapix.library.bukkit.panel.dsl

import org.bukkit.entity.Player
import ru.snapix.library.Replacement
import ru.snapix.library.bukkit.panel.Item
import ru.snapix.library.bukkit.panel.type.PagedPanel
import kotlin.time.Duration

fun pagedPanel(player: Player, builder: PagedPanelBuilder.() -> Unit): PagedPanel {
    return PagedPanelBuilder(player).apply(builder).build()
}

class PagedPanelBuilder(val player: Player) {
    var title: String? = null
    val items = mutableListOf<Item>()
    val layout = mutableListOf<List<String>>()
    var replacements: Replacement = Replacement()
    var update: Duration? = null
    var updateReplacements: (String) -> String = { it }

    fun PagedPanelBuilder.layout(setup: PageBuilder.() -> Unit) {
        layout.addAll(PageBuilder().apply(setup).list)
    }

    fun PagedPanelBuilder.items(setup: ItemsBuilder.() -> Unit) {
        items.addAll(ItemsBuilder().apply(setup).items)
    }

    fun PagedPanelBuilder.replacements(setup: ReplacementsBuilder.() -> Unit) {
        replacements.putAll(ReplacementsBuilder().apply(setup).list)
    }

    fun build(): PagedPanel {
        return PagedPanel(
            player,
            title ?: "Kotlin Paged Panel",
            update,
            replacements,
            layout,
            items,
            updateReplacements
        )
    }
}