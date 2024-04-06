repositories {
    maven("https://mvn.exceptionflug.de/repository/exceptionflug-public/")
}

dependencies {
    annotationProcessor("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")

    implementateModule("module-common")

    compileOnly("net.luckperms:api:5.4")
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    compileOnly("dev.simplix:protocolize-api:2.3.3")
}