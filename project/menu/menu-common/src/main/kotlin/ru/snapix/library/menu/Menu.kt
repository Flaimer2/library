package ru.snapix.library.menu

interface Menu {
    val settings: MenuSettings
    var title: String

    fun update()
    fun update(slot: Int)
}