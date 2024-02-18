package algorithms

import algorithms.common.Color

/**
 * Algorithm for creating an image with a random solid color
 */
class SolidColor(val width: Int, val height: Int) : ImageCreating {
    override fun createBitmap(): Array<Array<String>> {
        val color = Color.randomHex()
        return Array(width) { Array(height) { color } }
    }
}
