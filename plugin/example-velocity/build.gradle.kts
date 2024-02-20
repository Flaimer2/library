dependencies {
    kapt("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")

    compileModule("platform-velocity")
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
}