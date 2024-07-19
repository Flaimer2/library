package ru.snapix.library

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.scheduler.ScheduledTask
import ru.snapix.library.velocity.VelocityPlugin
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

fun ProxyServer.repeatTask(repeatDelay: Duration, runnable: () -> Unit): ScheduledTask {
    return scheduler
        .buildTask(snapiLibrary, Runnable { runnable() })
        .repeat(repeatDelay.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .schedule()
}

fun ProxyServer.runLaterTask(delay: Duration, runnable: () -> Unit): ScheduledTask {
    return scheduler
        .buildTask(snapiLibrary, Runnable { runnable() })
        .delay(delay.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .schedule()
}

fun <E> ProxyServer.callEvent(event: E) {
    eventManager.fire(event)
}

fun <E> VelocityPlugin.callEvent(event: E) {
    server.eventManager.fire(event)
}