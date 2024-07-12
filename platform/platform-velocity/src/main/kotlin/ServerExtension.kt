package ru.snapix.library

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.scheduler.ScheduledTask
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

fun ProxyServer.repeatTask(repeatDelay: Duration, runnable: () -> Unit): ScheduledTask {
    return scheduler
        .buildTask(snapiLibrary, Runnable { runnable() })
        .repeat(repeatDelay.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .schedule()
}