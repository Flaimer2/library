plugins {
    alias(libs.plugins.plugin.yml.bukkit)
}

dependencies {
    implementate("common")

    implementation(libs.acf.paper)
    implementation(libs.xseries)

    compileOnly(libs.bukkit)
    compileOnly(libs.dazzleconf)
    compileOnly(libs.vault)
    compileOnly(libs.serialization)
    compileOnly(libs.placeholderapi)
    compileOnly(libs.coroutines)
    compileOnly(libs.adventure.minimessage)
    compileOnly(libs.adventure.bukkit)
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
    main = "ru.snapix.library.bukkit.SnapiLibraryBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
    depend = listOf("Vault")
    softDepend = listOf("LastLoginAPI")
}