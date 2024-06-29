plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    implementation("net.kyori:adventure-platform-bukkit:4.3.3")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")

    compileSpigotApi()
    compileOnly("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:$dazzleConfVersion")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    compileOnly("me.clip:placeholderapi:2.11.6")

    implementateCommon()
    implementation("co.aikar:acf-paper:$acfVersion")
    implementation("com.github.cryptomorin:XSeries:11.2.0")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    relocateDependency("co.aikar")
    relocateDependency("io.github.crackthecodeabhi")
    relocateDependency("mu")
    relocateDependency("space.arim")
    relocateDependency("net.kyori")
    relocateDependency("com.cryptomorin")
}

fun com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.relocateDependency(pkg: String) {
    relocate(pkg, libraryPackage)
}

bukkit {
    name = rootName
    main = "ru.snapix.library.SnapiLibraryBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
    depend = listOf("Vault")
    softDepend = listOf("LastLoginAPI")
}