package ru.snapix.library.menu.dsl

import ru.snapix.library.menu.Replacement

class ReplacementsBuilder {
    val list = Replacement()

    operator fun Pair<String, () -> Any>.unaryMinus() {
        list[first] = second
    }

    operator fun Array<Pair<String, () -> Any>>.unaryMinus() {
        list.putAll(toMap())
    }

    operator fun Replacement.unaryMinus() {
        list.putAll(this)
    }
}