dependencies {
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines)
    implementation(libs.serialization)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.mariadb) { exclude("com.github.waffle", "waffle-jna") }
    implementation(libs.kreds) { exclude("org.jetbrains.kotlin"); exclude("org.jetbrains.kotlinx") }
    implementation(libs.dazzleconf) { exclude(group = "org.yaml", module = "snakeyaml") }

    testImplementation(libs.exposed.core)
    testImplementation(libs.exposed.jdbc)
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly(libs.lastloginapi)
    compileOnly(libs.luckperms)
}

tasks.test {
    useJUnitPlatform()
}