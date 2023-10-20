package com.randomimagecreator.creators

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import com.randomimagecreator.common.ImageCreatorOptions

private const val DEPTH = 4

/**
 * Creates a [Bitmap] based on Sierpinski carpet algorithm.
 */
class SierpinskiCarpetCreator : ImageCreator() {
    private lateinit var bitmap: Bitmap
    private val color = Color.parseColor(generateRandomHexColorValue())

    override fun createBitmap(options: ImageCreatorOptions): Bitmap {
        bitmap = Bitmap.createBitmap(options.width, options.height, Bitmap.Config.ARGB_8888).apply {
            setHasAlpha(false)
        }

        val initialSquare = SierpinskiSquare(Rect(0, 0, 600, 600))
        performAlgorithm(initialSquare, 0)
        return bitmap
    }

    private fun performAlgorithm(square: SierpinskiSquare, n: Int) {
        val subSquares = square.divideInNineSubSquares()
        bitmap.applyColors(subSquares)
        for (subSquare in subSquares.filter { !it.isMiddle }) {
            if (n < DEPTH) {
                performAlgorithm(subSquare, n + 1)
            }
        }
    }

    private fun Bitmap.applyColors(squares: Set<SierpinskiSquare>) {
        squares.forEach {
            for (x in it.left until it.right) {
                for (y in it.top until it.bottom) {
                    setPixel(x, y, if (it.isMiddle) Color.WHITE else color!!)
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