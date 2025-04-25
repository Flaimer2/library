package ru.snapix.library.network.messenger

import kotlinx.serialization.Serializable

@Serializable
abstract class Action {
    abstract fun executeIncomingMessage()
}