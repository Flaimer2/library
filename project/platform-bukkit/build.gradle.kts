plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    implementateModule("module-common")

    compileOnly("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.2.1")
    compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
}

bukkit {
    main = "ru.snapix.library.SnapiLibraryBukkit"
    author = "Flaimer"
    website = "https://mcsnapix.ru"
}