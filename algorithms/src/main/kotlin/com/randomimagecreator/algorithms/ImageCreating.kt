package com.randomimagecreator.algorithms

interface ImageCreating {
    /**
     * Creates a 2 dimensional array of pixels where each pixel is represented as a hexadecimal color.
     */
    fun createImage(): Array<Array<String>>
}