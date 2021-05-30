package com.randomimagecreator.helpers

import java.util.*

/**
 * Converts the current date to a string with the specified format.
 *
 * @param   format  The format to use for the date.
 * @param   locale  The locale to use for the date formatter. ( Default will be used if not provided )
 */
fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = java.text.SimpleDateFormat(format, locale)
    return formatter.format(this)
}