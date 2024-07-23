package ru.snapix.library.utils


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

/**
 * Используйте миллисекунды при вызове
 * Формат вызова: HH - часы, mm - минуты, ss - секунды, MMM - миллисекунды
 * Пример: ss.MMM
 */
fun Long.toShortTime(format: String): String {
    var result = format
    var time = this

    if (format.contains("HH", ignoreCase = true)) {
        val hours = time / 3600000
        time -= (hours * 3600000)
        result = result.replace("HH", hours.toString(), ignoreCase = true)
    }

    if (format.contains("mm", ignoreCase = true)) {
        val minutes = time / 60000
        time -= (minutes * 60000)
        result = result.replace("mm", minutes.toString(), ignoreCase = true)
    }

    if (format.contains("ss", ignoreCase = true)) {
        val seconds = time / 1000
        time -= (seconds * 1000)
        result = result.replace("ss", seconds.toString(), ignoreCase = true)
    }

    if (format.contains("MMM", ignoreCase = true)) {
        result = result.replace("MMM", time.toString(), ignoreCase = true)
    }

    return result
}