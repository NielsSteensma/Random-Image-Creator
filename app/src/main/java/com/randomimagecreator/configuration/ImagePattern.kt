package com.randomimagecreator.configuration

import com.randomimagecreator.R
import com.randomimagecreator.common.generators.ImageGenerator
import com.randomimagecreator.common.generators.MandelbrotGenerator
import com.randomimagecreator.common.generators.PixelatedGenerator
import com.randomimagecreator.common.generators.SierpinskiCarpetGenerator
import com.randomimagecreator.common.generators.SolidColorGenerator

/**
 * Defines all possible image generation patterns.
 */
enum class ImagePattern(val localizationResourceId: Int) {
    SOLID(R.string.image_creator_solid),
    PIXELATED(R.string.image_creator_pixelated),
    MANDELBROT(R.string.image_creator_mandelbrot),
    SIERPINSKI_CARPET(R.string.image_creator_sierpinski);

    fun getImageGenerator(): ImageGenerator {
        return when (this) {
            SOLID -> SolidColorGenerator()
            PIXELATED -> PixelatedGenerator()
            MANDELBROT -> MandelbrotGenerator()
            SIERPINSKI_CARPET -> SierpinskiCarpetGenerator()
        }
    }
}
