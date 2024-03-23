package ru.snapix.example

import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin

@Plugin(
    id = "snapivelocity",
    name = "SnapiVelocity",
    version = "1.0",
    authors = ["Flaimer"],
    dependencies = [Dependency(id = "snapilibrary")]
)
class SnapiVelocity