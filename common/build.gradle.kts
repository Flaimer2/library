dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines)
    implementation(libs.serialization)
    implementation(libs.bundles.exposed)
    implementation(libs.mariadb) { exclude("com.github.waffle", "waffle-jna") }
    implementation(libs.kreds) { exclude("org.jetbrains.kotlin"); exclude("org.jetbrains.kotlinx") }
    implementation(libs.dazzleconf) { exclude(group = "org.yaml", module = "snakeyaml") }
    implementation(libs.adventure.minimessage)
    implementation(libs.adventure.bukkit)

    compileOnly(libs.lastloginapi)
    compileOnly(libs.luckperms)
    compileOnly(libs.bukkit)
    compileOnly(libs.velocityapi)
    compileOnly(libs.balancer)
}