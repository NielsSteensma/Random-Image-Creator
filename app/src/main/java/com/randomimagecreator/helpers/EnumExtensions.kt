package com.randomimagecreator.helpers

/**
 * Returns the name of the current value with the first letter capitalized.
 */
fun Enum<*>.capitalized() = this.name.lowercase().replaceFirstChar { it.uppercaseChar() }
