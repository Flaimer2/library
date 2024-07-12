dependencies {
    implementation(libs.acf.velocity)

    implementate("common")

    kapt(libs.velocityapi)
    compileOnly(libs.velocityapi)
    compileOnly(libs.protocolize)
    compileOnly(libs.coroutines)
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    relocateDependency("co.aikar")
    relocateDependency("io.github.crackthecodeabhi")
    relocateDependency("mu")
    relocateDependency("space.arim")
    relocateDependency("org.jetbrains.kotlin")
}

fun com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.relocateDependency(pkg: String) {
    relocate(pkg, libraryPackage)
}