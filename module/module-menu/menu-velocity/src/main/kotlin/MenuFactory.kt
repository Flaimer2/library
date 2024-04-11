package ru.snapix.library.menu

object MenuFactory {
    fun createMenu(type: MenuType) = when (type) {
        MenuType.DEFAULT -> VelocityMenu()
        MenuType.PAGED -> VelocityMenu()
    }
}