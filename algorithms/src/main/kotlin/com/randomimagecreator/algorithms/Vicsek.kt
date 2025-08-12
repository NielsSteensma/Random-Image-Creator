package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color
import com.randomimagecreator.algorithms.common.Image
import com.randomimagecreator.algorithms.common.Rect

private const val DEPTH = 4

/**
 * Algorithm for creating an image based on Vicsek.
 */
class Vicsek(val width: Int, val height: Int) : ImageCreating {
    lateinit var color: String

    override fun createImage(): Array<Array<String>> {
        color = Color.randomHex()
        val image = Image.new(width, height)
        val initialSquare = Square(Rect(0, 0, width, height))
        performAlgorithm(image, initialSquare, 0)
        return image.pixels
    }

    private fun performAlgorithm(image: Image, square: Square, n: Int) {
        val subSquares = square.divideInNineSubSquares()
        for ((index, subSquare) in subSquares.withIndex()) {
            val isSquareInMiddle = index == 4
            val isSquareInCorner = setOf(0,2,6,8).contains(index)
            val color = if (isSquareInMiddle || isSquareInCorner) color else Color.WHITE
            image.applyColor(subSquare, color)
            if (n < DEPTH && (isSquareInMiddle || isSquareInCorner)) {
                performAlgorithm(image, subSquare, n + 1)
            }
        }
    }
}
