package ru.snapix.library.menu

abstract class Menu<T> {
    abstract val type: MenuType
    abstract var title: String
    abstract var rows: Int

    abstract fun addItem(item: T)
    abstract fun setItem(item: T, slot: Int)
    abstract fun removeItem(slot: Int)
}