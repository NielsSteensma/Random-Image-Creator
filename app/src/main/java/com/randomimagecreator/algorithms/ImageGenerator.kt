package com.randomimagecreator.algorithms

import android.graphics.Bitmap
import com.randomimagecreator.ImageCreatorOptions

/**
 *  Base class for usage by classes that generate images.
 */
abstract class ImageGenerator {
    abstract fun generateBitmap(options: ImageCreatorOptions? = null): Bitmap
    protected abstract fun generateSingleBitmap(options: ImageCreatorOptions? = null): Bitmap
}