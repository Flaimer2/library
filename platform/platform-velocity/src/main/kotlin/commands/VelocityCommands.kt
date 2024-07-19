package ru.snapix.library.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.VelocityCommandManager
import com.velocitypowered.api.proxy.ProxyServer

abstract class VelocityCommands(server: ProxyServer, plugin: Any, vararg val commands: BaseCommand) {
    val manager = VelocityCommandManager(server, plugin)
    val commandCompletions get() = manager.commandCompletions
    val commandReplacements get() = manager.commandReplacements

    fun enable() {
        registerCommandCompletions()
        registerCommandReplacements()
        commands.forEach { manager.registerCommand(it) }
    }

    fun disable() {
        manager.unregisterCommands()
    }

    abstract fun registerCommandCompletions()
    abstract fun registerCommandReplacements()
}