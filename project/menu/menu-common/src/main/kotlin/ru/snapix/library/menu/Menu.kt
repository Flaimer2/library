package ru.snapix.library.menu

abstract class Menu {
    abstract val type: MenuType
    abstract var title: String
    abstract var rows: Int
//    abstract var updateDelay: Long

    abstract fun update()
    abstract fun update(slot: Int)
    abstract fun addItem(item: MenuItem)
    abstract fun setItem(item: MenuItem, slot: Int)
    abstract fun setTitle(title: String)
    abstract fun setRows(rows: Int)
}