package ru.snapix.library.common.utils

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Rounds value to x places
 *
 * @param places places
 * @return rounded value
 * @throws IllegalArgumentException when places to round is lower than 0
 */
fun BigDecimal.round(places: Int): BigDecimal {
    require(places >= 0)

    setScale(places, RoundingMode.HALF_UP)
    return this
}

/**
 * Rounds value to x places
 *
 * @param places places
 * @return rounded value
 * @throws IllegalArgumentException when places to round is lower than 0
 */
fun Double.round(places: Int): Double {
    val bigDecimal = toBigDecimal()
    val rounded = bigDecimal.round(places)
    return rounded.toDouble()
}

/**
 * Rounds value to x places
 *
 * @param places places
 * @return rounded value
 * @throws IllegalArgumentException when places to round is lower than 0
 */
fun Float.round(places: Int): Float {
    val bigDecimal = toBigDecimal()
    val rounded = bigDecimal.round(places)
    return rounded.toFloat()
}