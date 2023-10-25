package com.randomimagecreator.common.extensions

/**
 * Converts [CharSequence] of numbers to [Int].
 *
 * @return [Int] when succeeded, null if conversion failed or [CharSequence] is not digit only.
 */
fun CharSequence.toInt(): Int? {
    if (this.isBlank() || this.any { !it.isDigit() }) return null
    return try {
        Integer.parseInt(this.toString())
    } catch (error: NumberFormatException) {
        null
    }
}