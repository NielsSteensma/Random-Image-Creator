package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Rect

internal class Square(
    rect: Rect
) {
    val left: Int = rect.left
    val right: Int = rect.right
    val top: Int = rect.top
    val bottom: Int = rect.bottom

    /**
     * Divides current square in nine sub squares.
     *
     * Indices of the returned [LinkedHashSet] can be used to determine the sub square's position
     * in the parent.
     *
     * See also the following mapping:
     * ```
     * 0  3  6
     * 1  4  7
     * 2  5  8
     */
    fun divideInNineSubSquares(): LinkedHashSet<Square> {
        val sideLength = (right - left) / 3

        val squares = linkedSetOf<Square>()
        for (x in 0..2) {
            for (y in 0..2) {
                val rect = Rect(
                    left + (sideLength * x),
                    top + (sideLength * y),
                    left + (sideLength * (x + 1)),
                    top + (sideLength * (y + 1))
                )
                squares.add(Square(rect))
            }
        }
        return squares
    }
}