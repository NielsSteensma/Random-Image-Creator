package com.randomimagecreator.creators

import android.graphics.Bitmap
import com.randomimagecreator.ImageCreatorOptions

/**
 *  Base class for usage by classes that generate images.
 */
abstract class ImageCreator {
    /**
     * Returns an array of randomly created bitmaps with the generation algorithm of the class.
     *
     * @param imageCreatorOptions   Any custom options to set for the generated bitmaps.
     */
    fun createBitmaps(imageCreatorOptions: ImageCreatorOptions? = null): MutableList<Bitmap> {
        val options = imageCreatorOptions ?: ImageCreatorOptions()
        val bitmaps = mutableListOf<Bitmap>()
        for (i in 0..options.amount) {
            bitmaps.add(createBitmap(options))
        }
        return bitmaps
    }

    protected abstract fun createBitmap(imageCreatorOptions: ImageCreatorOptions): Bitmap
}