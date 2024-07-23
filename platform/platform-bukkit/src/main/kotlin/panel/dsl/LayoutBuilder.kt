package ru.snapix.library.bukkit.panel.dsl

class LayoutBuilder {
    val list = mutableListOf<String>()

    operator fun String.unaryMinus() {
        list.add(this)
    }
}