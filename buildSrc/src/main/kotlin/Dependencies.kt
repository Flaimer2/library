import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.*

fun DependencyHandler.compileModule(vararg name: String) {
    name.forEach { add("compileOnly", project(":project:$it")) }
}

fun DependencyHandler.implementateModule(vararg name: String) {
    name.forEach { add("implementation", project(":project:$it")) }
}
