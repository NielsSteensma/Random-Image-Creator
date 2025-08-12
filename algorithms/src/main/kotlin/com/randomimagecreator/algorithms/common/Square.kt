package com.randomimagecreator.algorithms.common

internal data class Square(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
) {

    init {
        require((right - left) == (bottom - top)) {
            "Width and height must be equal"
        }
    }

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
                val square = Square(
                    left + (sideLength * x),
                    top + (sideLength * y),
                    left + (sideLength * (x + 1)),
                    top + (sideLength * (y + 1))
                )
                squares.add(square)
            }
        }
        return squares
    }
}