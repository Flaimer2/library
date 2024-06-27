package ru.snapix.library

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Используйте миллисекунды при вызове
 */
fun Long.toDate(format: String): String {
    val dateFormat = DateTimeFormatter.ofPattern(format)
    val formattedDateTime: String = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).format(dateFormat)
    return formattedDateTime
}