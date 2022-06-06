package com.randomimagecreator.helpers

/**
 * Returns the values of the given [Enum] as a capitalized [Array] of [String].
 */
inline fun <reified E : Enum<E>> capitalizedValuesOf() =
    enumValues<E>().map { enumValue ->
        enumValue.toString().lowercase()
            .replaceFirstChar { it.titlecase() }
    }
