package ru.snapix.library.bukkit.commands

import ru.snapix.library.SnapiLibrary
import ru.snapix.library.bukkit.BukkitCommands
import ru.snapix.library.bukkit.plugin

internal object Commands : BukkitCommands(plugin, SocialCommand()) {
    override fun registerCommandCompletions() {
        manager.commandCompletions.registerCompletion("empty") {
            emptyList<String>()
        }
        manager.commandCompletions.registerCompletion("players") {
            SnapiLibrary.getOnlinePlayers().map { it.getName() }
        }
    }
    override fun registerCommandReplacements() {}
}