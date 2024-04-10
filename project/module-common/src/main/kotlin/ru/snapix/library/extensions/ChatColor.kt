package ru.snapix.library.extensions

fun translateAlternateColorCodes(altColorChar: Char, textToTranslate: String): String {
    val b = textToTranslate.toCharArray()
    for (i in 0 until b.size - 1) {
        if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
            b[i] = '\u00A7'
            b[i + 1] = b[i + 1].lowercaseChar()
        }
    }
    return String(b)
}

fun translateAlternateColorCodes(textToTranslate: String): String {
    return translateAlternateColorCodes('&', textToTranslate)
}