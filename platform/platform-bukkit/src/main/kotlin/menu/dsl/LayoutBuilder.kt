package ru.snapix.library.menu.dsl

class LayoutBuilder {
    val list = mutableListOf<String>()

    operator fun String.unaryMinus() {
        list.add(this)
    }
}