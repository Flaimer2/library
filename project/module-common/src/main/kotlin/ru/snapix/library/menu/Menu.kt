package ru.snapix.library.menu

interface Menu {
    val settings: MenuSettings

    fun update()
    fun update(slot: Int)
    fun <T> addItem(item: T)
    fun <T> setItem(slot: Int, item: T)
    fun setTitle(title: String)
}