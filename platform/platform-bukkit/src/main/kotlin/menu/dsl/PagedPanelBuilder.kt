package ru.snapix.library.menu.dsl

import org.bukkit.entity.Player
import ru.snapix.library.menu.Replacement
import ru.snapix.library.menu.items.Item
import ru.snapix.library.menu.panels.PagedPanel
import ru.snapix.library.menu.panels.StandardPanel
import kotlin.time.Duration

fun pagedPanel(player: Player, builder: PagedPanelBuilder.() -> Unit): PagedPanel {
    return PagedPanelBuilder(player).apply(builder).build()
}

class PagedPanelBuilder(val player: Player) {
    var title: String? = null
    val items = mutableListOf<Item>()
    val layout = mutableListOf<List<String>>()
    val replacements = mutableListOf<Replacement>()
    var update: Duration? = null

    fun PagedPanelBuilder.layout(setup: PageBuilder.() -> Unit) {
        layout.addAll(PageBuilder().apply(setup).list)
    }

    fun PagedPanelBuilder.items(setup: ItemsBuilder.() -> Unit) {
        items.addAll(ItemsBuilder().apply(setup).items)
    }

    fun PagedPanelBuilder.replacements(setup: ReplacementsBuilder.() -> Unit) {
        replacements.addAll(ReplacementsBuilder().apply(setup).list)
    }

    fun build(): PagedPanel {
        return PagedPanel(player, title ?: "Kotlin Paged Panel", update, replacements, layout, items)
    }
}