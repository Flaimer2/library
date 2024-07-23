package ru.snapix.library.network.panel

import ru.snapix.library.Replacement
import kotlin.time.Duration

interface Panel {
    val title: String
    val update: Duration?
    val replacements: Replacement
    val updateReplacements: (String) -> String

    fun disable()
    fun onOpen()
    fun onClose()
}