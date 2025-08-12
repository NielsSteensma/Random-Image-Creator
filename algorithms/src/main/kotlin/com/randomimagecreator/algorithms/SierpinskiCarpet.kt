package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color
import com.randomimagecreator.algorithms.common.Image
import com.randomimagecreator.algorithms.common.Rect

private const val DEPTH = 4

/**
 * Algorithm for creating an image based on Sierpinski carpet.
 */
class SierpinskiCarpet(val width: Int, val height: Int) : ImageCreating {
    lateinit var color: String

    override fun createImage(): Array<Array<String>> {
        if (width % 3 != 0) {
            throw IllegalArgumentException("Width is not dividable by 3")
        }
        if (height % 3 != 0) {
            throw IllegalArgumentException("Height is not dividable by 3")
        }
        if (width != height) {
            throw IllegalArgumentException("Width and height are not equal")
        }

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
            val color = if (isSquareInMiddle) Color.WHITE else color
            image.applyColor(subSquare, color)
            if (n < DEPTH && !isSquareInMiddle) {
                performAlgorithm(image, subSquare, n + 1)
            }
        }
    }
}