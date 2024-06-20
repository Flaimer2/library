dependencies {
    implementation(kotlin("stdlib"))
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:$dazzleConfVersion") {
        exclude(group = "org.yaml", module = "snakeyaml")
    }
    implementation("io.github.crackthecodeabhi:kreds:$kredsVersion") {
        exclude("org.jetbrains.kotlin")
        exclude("org.jetbrains.kotlinx")
        exclude("io.netty")
        exclude("org.slf4j")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion") {
        exclude("org.slf4j")
    }
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariadbVersion") {
        exclude("com.github.waffle", "waffle-jna")
    }
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("com.alessiodp.lastloginapi:lastloginapi-api:1.7.4")
}

tasks.test {
    useJUnitPlatform()
}