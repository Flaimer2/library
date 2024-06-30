dependencies {
    implementation(libs.acf.velocity)

    compile("common")
    compileOnly(libs.velocityapi)
    kapt(libs.velocityapi)
    compileOnly(libs.protocolize)
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