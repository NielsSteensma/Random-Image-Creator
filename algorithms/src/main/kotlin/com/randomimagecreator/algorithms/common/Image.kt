package com.randomimagecreator.algorithms.common

import com.randomimagecreator.algorithms.Square

internal class Image(val pixels: Array<Array<String>>) {

    fun applyColor(square: Square, color: String) {
        for (x in square.left until square.right) {
            for (y in square.top until square.bottom) {
                pixels[x][y] = color
            }
        }
    }

    companion object {
        fun new(width: Int, height: Int): Image {
            return Image(Array(width) { Array(height) { "" } })
        }
    }
}