package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color
import com.randomimagecreator.algorithms.common.Rect


private const val DEPTH = 4

/**
 * Algorithm for creating an image based on Sierpinski carpet.
 */
object SierpinskiCarpet {

    /**
     * Creates a 2 dimensional array of pixels where each pixel is represented as a hexadecimal color.
     */
    fun createImage(width: Int, height: Int): Array<Array<String>> {
        if (width % 3 != 0) {
            throw IllegalArgumentException("Width is not dividable by 3")
        }
        if (height % 3 != 0) {
            throw IllegalArgumentException("Height is not dividable by 3")
        }
        if (width != height) {
            throw IllegalArgumentException("Width and height are not equal")
        }

        val color = Color.randomHex()
        val image = Array(width) { Array(height) { "" } }
        val initialSquare = SierpinskiSquare(Rect(0, 0, width, height))
        performAlgorithm(image, initialSquare, 0, color)
        return image
    }

    private fun performAlgorithm(image: Array<Array<String>>, square: SierpinskiSquare, n: Int, color: String) {
        val subSquares = square.divideInNineSubSquares()
        image.applyColors(subSquares, color)
        for (subSquare in subSquares.filter { !it.isMiddle }) {
            if (n < DEPTH) {
                performAlgorithm(image, subSquare, n + 1, color)
            }
        }
    }

    private fun Array<Array<String>>.applyColors(squares: Set<SierpinskiSquare>, color: String) {
        squares.forEach {
            for (x in it.left until it.right) {
                for (y in it.top until it.bottom) {
                    this[x][y] = if (it.isMiddle) Color.WHITE else color
                }
            }
        }
    }
}


private class SierpinskiSquare(rect: Rect, val isMiddle: Boolean = false) {
    val left = rect.left
    val right = rect.right
    val top = rect.top
    val bottom = rect.bottom

    /**
     * Divides current square in nine sub squares.
     */
    fun divideInNineSubSquares(): Set<SierpinskiSquare> {
        val sideLength = (right - left) / 3

        val squares = mutableSetOf<SierpinskiSquare>()
        for (x in 0..2) {
            for (y in 0..2) {
                val rect = Rect(
                    left + (sideLength * x),
                    top + (sideLength * y),
                    left + (sideLength * (x + 1)),
                    top + (sideLength * (y + 1))
                )
                squares.add(SierpinskiSquare(rect, x == 1 && y == 1))
            }
        }
        return squares
    }
}