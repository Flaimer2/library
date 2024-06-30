import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.implementate(vararg name: String) {
    name.forEach { add("implementation", project(":$it")) }
}

fun DependencyHandler.compile(vararg name: String) {
    name.forEach { add("compileOnly", project(":$it")) }
}
//fun DependencyHandler.compileModule(vararg name: String) {
//    name.forEach { add("compileOnly", project(":module:$it")) }
//}
//
//fun DependencyHandler.implementateModule(vararg name: String) {
//    name.forEach { add("implementation", project(":module:$it")) }
//}
//
//fun DependencyHandler.compileCommon() {
//    add("compileOnly", project(":common"))
//}
//
//fun DependencyHandler.implementateCommon() {
//    add("implementation", project(":common"))
//}

//fun DependencyHandler.compileVelocity() {
//    add("compileOnly", project(":platform:platform-velocity"))
//}
//
//fun DependencyHandler.implementateVelocity() {
//    add("implementation", project(":platform:platform-velocity"))
//}
//
//fun DependencyHandler.compileBukkit() {
//    add("compileOnly", project(":platform:platform-bukkit"))
//}
//
//fun DependencyHandler.implementateBukkit() {
//    add("implementation", project(":platform:platform-bukkit"))
//}
