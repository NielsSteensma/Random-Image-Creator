package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color

/**
 * Algorithm for creating an image with each pixel having a random solid color
 */
class Pixelated(val width: Int, val height: Int) : ImageCreating {
    override fun createImage(): Array<Array<String>> {
        return Array(width) { Array(height) { Color.randomHex() } }
    }
}
