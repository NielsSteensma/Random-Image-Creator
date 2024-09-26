package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color

/**
 * Algorithm for creating an image with each pixel having a random solid color
 */
object Pixelated {

    /**
     * Creates a 2 dimensional array of pixels where each pixel is represented as a hexadecimal color.
     */
    fun createImage(width: Int, height: Int): Array<Array<String>> {
        return Array(width) { Array(height) { Color.randomHex() } }
    }
}
