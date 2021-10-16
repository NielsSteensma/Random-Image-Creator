package com.randomimagecreator.helpers

/**
 * Converts a string to an int, in case the given string is null or empty, 0 is returned.
 *
 * @param   value  The value to convert to an [Int].
 * @return The result of the conversion.
 */
fun Int.Companion.parse(value: CharSequence?): Int {
    val safeValue = if (value == null || value.isBlank()) "0" else value.toString()
    return Integer.parseInt(safeValue)
}
