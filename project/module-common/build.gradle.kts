dependencies {
    implementation("co.aikar:idb-core:1.0.0-SNAPSHOT")
    implementation("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.2.1")
    implementation("redis.clients:jedis:5.1.0")
    implementation(kotlin("stdlib"))
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.2") {
        exclude("com.github.waffle", "waffle-jna")
    }
}