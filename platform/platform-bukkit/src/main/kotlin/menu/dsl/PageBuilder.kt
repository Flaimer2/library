package ru.snapix.library.menu.dsl

class PageBuilder {
    val list = mutableListOf<List<String>>()

    operator fun List<String>.unaryMinus() {
        list.add(this)
    }
}