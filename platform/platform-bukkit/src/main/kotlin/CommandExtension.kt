package ru.snapix.library

import co.aikar.commands.CommandReplacements

fun CommandReplacements.addReplacements(vararg replacements: Pair<String, String>) {
    addReplacements("", *replacements)
}

fun CommandReplacements.addReplacements(key: String, vararg replacements: Pair<String, String>) {
    replacements.forEach {
        addReplacement(key + it.first, it.second)
    }
}