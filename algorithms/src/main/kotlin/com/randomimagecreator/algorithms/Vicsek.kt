package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color
import com.randomimagecreator.algorithms.common.Rect

private const val DEPTH = 4

/**
 * Algorithm for creating an image based on Vicsek.
 */
class Vicsek(val width: Int, val height: Int) : ImageCreating {
    lateinit var color: String

    override fun createImage(): Array<Array<String>> {

        color = Color.randomHex()
        val image = Array(width) { Array(height) { "" } }
        val initialSquare = VicsekSquare(Rect(0, 0, width, height))
        performAlgorithm(image, initialSquare, 0)
        return image
    }

    private fun performAlgorithm(image: Array<Array<String>>, square: VicsekSquare, n: Int) {
        val subSquares = square.divideInNineSubSquares()
        image.applyColors(subSquares)
        for (subSquare in subSquares.filter { it.isMiddle || it.isCorner }) {
            if (n < DEPTH) {
                performAlgorithm(image, subSquare, n + 1)
            }
        }
    }

    private fun Array<Array<String>>.applyColors(squares: Set<VicsekSquare>) {
        squares.forEach {
            for (x in it.left until it.right) {
                for (y in it.top until it.bottom) {
                    this[x][y] = if (it.isMiddle || it.isCorner) color else Color.WHITE
                }
            }
        }
    }
}

private class VicsekSquare(rect: Rect, val isMiddle: Boolean = false, val isCorner: Boolean = false) {
    val left = rect.left
    val right = rect.right
    val top = rect.top
    val bottom = rect.bottom

    /**
     * Divides current square in nine sub squares.
     */
    fun divideInNineSubSquares(): Set<VicsekSquare> {
        val sideLength = (right - left) / 3

        val squares = mutableSetOf<VicsekSquare>()
        for (x in 0..2) {
            for (y in 0..2) {
                val rect = Rect(
                    left + (sideLength * x),
                    top + (sideLength * y),
                    left + (sideLength * (x + 1)),
                    top + (sideLength * (y + 1))
                )
                val isMiddle = x == 1 && y == 1
                val isCorner = (x == 0 || x == 2) && (y == 0 || y == 2)
                squares.add(VicsekSquare(rect, isMiddle, isCorner))
            }
        }
        return squares
    }
}