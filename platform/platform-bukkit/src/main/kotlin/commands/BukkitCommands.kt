package ru.snapix.library.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.PaperCommandManager
import org.bukkit.plugin.java.JavaPlugin

abstract class BukkitCommands(plugin: JavaPlugin, vararg val commands: BaseCommand) {
    val manager = PaperCommandManager(plugin)
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