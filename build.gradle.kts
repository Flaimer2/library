plugins {
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("com.github.johnrengelman.shadow") version shadowJarVersion
    `maven-publish`
}

allprojects {
    group = rootGroup
    version = rootVersion

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.aikar.co/content/groups/aikar/")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "maven-publish")

    dependencies {
        compileOnly(kotlin("stdlib"))
        compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    }

    tasks.build {
        dependsOn("shadowJar")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-XDenableSunApiLintControl"))
    }
}

subprojects
    .filter { it.name.startsWith("platform") }
    .forEach { proj ->
        proj.publishing { applyToSub(proj) }
    }

fun PublishingExtension.applyToSub(subProject: Project) {
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.name.toLowerCase()
            groupId = rootGroup
            version = "$rootVersion-${subProject.name.removePrefix("platform-")}"
        }
    }
}