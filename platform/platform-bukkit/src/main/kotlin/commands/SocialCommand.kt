package ru.snapix.library.bukkit.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.Default
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("social")
internal class SocialCommand : BaseCommand() {
    @Default
    @CommandCompletion("@players @empty")
    fun default(player: Player, args: Array<String>) {
        if (args.isEmpty()) {
            Bukkit.dispatchCommand(player, "bettersocial edit")
            return
        }
        val name = args[0]
        Bukkit.dispatchCommand(player, "bettersocial menu $name")
    }
}