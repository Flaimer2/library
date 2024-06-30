package ru.snapix.library

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlin.time.Duration

@OptIn(ObsoleteCoroutinesApi::class, DelicateCoroutinesApi::class)
fun runTaskTimer(period: Duration, initialDelay: Duration = Duration.ZERO, runnable: () -> Unit): ReceiveChannel<Unit> {
    val ticker = ticker(period.inWholeMilliseconds, initialDelay.inWholeMilliseconds)
    GlobalScope.launch(Dispatchers.Unconfined) {
        for (event in ticker) {
            runnable()
        }
    }
    return ticker
}