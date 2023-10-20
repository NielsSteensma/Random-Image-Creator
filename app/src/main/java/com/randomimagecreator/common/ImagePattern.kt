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
enum class ImagePattern(val localizationResourceId: Int) {
    SOLID(R.string.image_creator_solid),
    PIXELATED(R.string.image_creator_pixelated),
    MANDELBROT(R.string.image_creator_mandelbrot),
    SIERPINSKI_CARPET(R.string.image_creator_sierpinski);

    fun getImageCreator(): ImageCreator {
        return when (this) {
            SOLID -> SolidColorCreator()
            PIXELATED -> PixelatedCreator()
            MANDELBROT -> MandelbrotCreator()
            SIERPINSKI_CARPET -> SierpinskiCarpetCreator()
        }
    }
}
