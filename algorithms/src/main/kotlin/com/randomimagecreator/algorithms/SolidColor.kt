package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color

/**
 * Algorithm for creating an image with a random solid color
 */
object SolidColor {

    /**
     * Creates a 2 dimensional array of pixels where each pixel is represented as a hexadecimal color.
     */
    fun createImage(width: Int, height: Int): Array<Array<String>> {
        val color = Color.randomHex()
        return Array(width) { Array(height) { color } }
    }
}
