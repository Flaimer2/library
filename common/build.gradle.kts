dependencies {
    implementation(kotlin("stdlib"))
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:$dazzleConfVersion") {
        exclude(group = "org.yaml", module = "snakeyaml")
    }
    implementation("io.github.crackthecodeabhi:kreds:$kredsVersion") {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariadbVersion") {
        exclude("com.github.waffle", "waffle-jna")
    }
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    testImplementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("com.alessiodp.lastloginapi:lastloginapi-api:1.7.4")
}

fun com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.relocateDependency(pkg: String) {
    relocate(pkg, libraryPackage)
}

tasks.test {
    useJUnitPlatform()
}