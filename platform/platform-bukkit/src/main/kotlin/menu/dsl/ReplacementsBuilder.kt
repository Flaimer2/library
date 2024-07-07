package ru.snapix.library.menu.dsl

import ru.snapix.library.menu.Replacement

class ReplacementsBuilder {
    val list = mutableListOf<Replacement>()

    operator fun Pair<String, () -> Any>.unaryMinus() {
        list.add(this)
    }
}