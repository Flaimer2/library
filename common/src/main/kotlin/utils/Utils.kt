package ru.snapix.library.utils

import java.net.URL

fun fromURL(url: String): String {
    return try {
        String(URL(url).openStream().readBytes())
    } catch (t: Throwable) {
        ""
    }
}
