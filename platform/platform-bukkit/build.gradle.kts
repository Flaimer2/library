plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    compileSpigotApi()
    implementateCommon()
    implementation("co.aikar:acf-paper:$acfVersion")
    implementateModule("module-menu:menu-bukkit")
    compileOnly("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:$dazzleConfVersion")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    relocateDependency("co.aikar")
    relocateDependency("io.github.crackthecodeabhi")
    relocateDependency("mu")
    relocateDependency("space.arim")
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