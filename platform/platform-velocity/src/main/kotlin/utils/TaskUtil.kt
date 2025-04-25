package ru.snapix.library.velocity.utils

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.scheduler.ScheduledTask
import ru.snapix.library.velocity.plugin
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

fun ProxyServer.repeatTask(repeatDelay: Duration, runnable: () -> Unit): ScheduledTask {
    return scheduler
        .buildTask(plugin, Runnable { runnable() })
        .repeat(repeatDelay.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .schedule()
}

fun ProxyServer.runLaterTask(delay: Duration, runnable: () -> Unit): ScheduledTask {
    return scheduler
        .buildTask(plugin, Runnable { runnable() })
        .delay(delay.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        .schedule()
}