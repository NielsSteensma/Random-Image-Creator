package com.randomimagecreator.configuration

import com.randomimagecreator.R

/**
 * Defines all possible image generation patterns.
 */
enum class ImagePattern(val localizationResourceId: Int) {
    SOLID(R.string.image_creator_solid),
    PIXELATED(R.string.image_creator_pixelated),
    MANDELBROT(R.string.image_creator_mandelbrot),
    SIERPINSKI_CARPET(R.string.image_creator_sierpinski),
    VICSEK(R.string.image_creator_vicsek);
}
