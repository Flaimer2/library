package ru.snapix.library.menu.dsl

import org.bukkit.entity.Player
import ru.snapix.library.menu.Item
import ru.snapix.library.menu.Replacement
import ru.snapix.library.menu.panels.GeneratorPanel
import kotlin.time.Duration

fun <T> generatorPanel(player: Player, builder: GeneratorBuilder<T>.() -> Unit): GeneratorPanel<T> {
    return GeneratorBuilder<T>(player).apply(builder).build()
}

class GeneratorBuilder<T>(val player: Player) {
    var title: String? = null
    val items = mutableListOf<Item>()
    val layout = mutableListOf<String>()
    val replacements = mutableListOf<Replacement>()
    var update: Duration? = null
    private var generatorSource: () -> List<T> = { emptyList() }
    var generatorOutput: (T) -> Item? = { null }
    private var filter: (T) -> Boolean = { true }
    var comparator: Comparator<T> = compareBy { 0 }

    fun GeneratorBuilder<T>.layout(setup: LayoutBuilder.() -> Unit) {
        layout.addAll(LayoutBuilder().apply(setup).list)
    }

    fun GeneratorBuilder<T>.items(setup: ItemsBuilder.() -> Unit) {
        items.addAll(ItemsBuilder().apply(setup).items)
    }

    fun GeneratorBuilder<T>.replacements(setup: ReplacementsBuilder.() -> Unit) {
        replacements.addAll(ReplacementsBuilder().apply(setup).list)
    }

    fun GeneratorBuilder<T>.generatorSource(setup: () -> List<T>) {
        generatorSource = setup
    }

    fun GeneratorBuilder<T>.filter(setup: (T) -> Boolean) {
        filter = setup
    }

    fun build(): GeneratorPanel<T> {
        return GeneratorPanel(
            player,
            title ?: "Kotlin Generator Panel",
            update,
            replacements,
            layout,
            items,
            generatorSource,
            generatorOutput,
            filter,
            comparator
        )
    }
}
