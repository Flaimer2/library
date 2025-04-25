package ru.snapix.library.utils

import java.lang.reflect.Field
import kotlin.reflect.KClass

fun <T : Any> fieldNames(clazz: KClass<T>): List<String> {
    val result = mutableListOf<String>()
    val fields: Array<Field> = clazz.java.getDeclaredFields()

    for (field in fields) {
        result.add(field.name)
    }

    return result
}
