dependencies {
    implementation(kotlin("stdlib"))
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:$dazzleConfVersion")
    implementation("redis.clients:jedis:$redisVersion")
    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariadb") {
        exclude("com.github.waffle", "waffle-jna")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}