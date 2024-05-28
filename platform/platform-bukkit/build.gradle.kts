plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    compileSpigotApi()
    implementateCommon()
    implementation("co.aikar:acf-bukkit:$acfVersion")
    implementateModule("module-menu:menu-bukkit")
    compileOnly("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:$dazzleConfVersion")
}

bukkit {
    name = rootName
    main = "ru.snapix.library.SnapiLibraryBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
}