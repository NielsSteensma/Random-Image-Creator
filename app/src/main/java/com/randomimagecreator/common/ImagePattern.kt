package com.randomimagecreator.common

import com.randomimagecreator.R
import com.randomimagecreator.creators.ImageCreator
import com.randomimagecreator.creators.MandelbrotCreator
import com.randomimagecreator.creators.PixelatedCreator
import com.randomimagecreator.creators.SierpinskiCarpetCreator
import com.randomimagecreator.creators.SolidColorCreator

/**
 * Defines all possible image creation patterns.
 */
enum class ImagePattern(
    val imageCreator: ImageCreator,
    val localizationResourceId: Int) {
    SOLID(SolidColorCreator(), R.string.image_creator_solid),
    PIXELATED(PixelatedCreator(), R.string.image_creator_pixelated),
    MANDELBROT(MandelbrotCreator(), R.string.image_creator_mandelbrot),
    SIERPINSKI_CARPET(SierpinskiCarpetCreator(), R.string.image_creator_sierpinski)
}
