package com.randomimagecreator.helpers

/**
 * Converts a [CharSequence] to an [Int],
 * in case the given [CharSequence] is null or empty, 0 is returned.
 *
 * @param   value  The value to convert to an [Int].
 * @return The result of the conversion.
 */
fun Int.Companion.parse(value: CharSequence?): Int {
    val safeValue = if (value == null || value.isBlank()) "0" else value.toString()
    return Integer.parseInt(safeValue)
}
