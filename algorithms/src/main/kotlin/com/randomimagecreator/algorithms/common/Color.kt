package com.randomimagecreator.algorithms.common

import kotlin.random.Random

internal object Color {
    const val WHITE = "#FFFFFF"
    const val BLACK = "#000000"

    /**
     * Returns a randomly generated hexadecimal color code as [String].
     *
     * @implNote This method works by selecting 6 times a random character of the list of random hexadecimal
     * color codes to create the final value.
     */
    fun randomHex(): String {
        val random = Random(System.currentTimeMillis())
        val possibleHexadecimalCharacters =
            charArrayOf('0', '1', '2', '3', '4', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        var color = "#"
        for (i in 0..5) {
            color += possibleHexadecimalCharacters[random.nextInt(0, 15)]
        }
        return color
    }
}