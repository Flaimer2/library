dependencies {
    implementation("co.aikar:idb-core:1.0.0-SNAPSHOT")
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.2.1")
    implementation("redis.clients:jedis:5.1.0")
    implementation(kotlin("stdlib"))
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3") {
        exclude("com.github.waffle", "waffle-jna")
    }
    implementation("com.zaxxer:HikariCP:5.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}