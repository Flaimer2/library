dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines)
    implementation(libs.serialization)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.mariadb) { exclude("com.github.waffle", "waffle-jna") }
    implementation(libs.kreds) { exclude("org.jetbrains.kotlin"); exclude("org.jetbrains.kotlinx") }
    implementation(libs.dazzleConf) { exclude(group = "org.yaml", module = "snakeyaml") }

    testImplementation(libs.exposed.core)
    testImplementation(libs.exposed.jdbc)

    compileOnly(libs.lastloginapi)
}

tasks.test {
    useJUnitPlatform()
}