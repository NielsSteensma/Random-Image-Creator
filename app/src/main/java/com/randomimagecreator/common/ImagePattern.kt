package com.randomimagecreator.common

import com.randomimagecreator.creators.ImageCreator
import com.randomimagecreator.creators.MandelbrotCreator
import com.randomimagecreator.creators.PixelatedCreator
import com.randomimagecreator.creators.SolidColorCreator

/**
 * Defines all possible image creation patterns.
 */
enum class ImagePattern(val imageCreator: ImageCreator) {
    SOLID(SolidColorCreator()),
    PIXELATED(PixelatedCreator()),
    MANDELBROT(MandelbrotCreator())
}
