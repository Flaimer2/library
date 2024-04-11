dependencies {
    compileVelocityApi()
    implementateCommon()
    implementation("co.aikar:acf-velocity:$acfVersion")
    implementateModule("module-menu:menu-velocity")
}