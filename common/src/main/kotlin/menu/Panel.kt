package ru.snapix.library.menu

import kotlin.time.Duration

interface Panel {
    val title: String
    val update: Duration?
    val replacements: List<Replacement>
    val updateReplacements: (String) -> String

    fun disable()
    fun onOpen()
    fun onClose()
}