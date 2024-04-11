plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "SnapiLibrary"

include("common")
include("platform:platform-bukkit")
include("platform:platform-velocity")
include("module:module-database")
include("module:module-menu:menu-common")
include("module:module-menu:menu-bukkit")
include("module:module-menu:menu-velocity")
