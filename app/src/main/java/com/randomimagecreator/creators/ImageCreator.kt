package com.randomimagecreator.creators

import android.graphics.Bitmap
import com.randomimagecreator.ImageCreatorOptions

/**
 *  Base class for generating images.
 */
abstract class ImageCreator {
    /**
     * Returns an array of randomly created bitmaps with the generation algorithm of the derived class.
     *
     * @param options   Any custom options to set for the generated bitmaps.
     */
    fun createBitmaps(options: ImageCreatorOptions): MutableList<Bitmap> {
        val createdBitmaps = mutableListOf<Bitmap>()
        for (i in 0 until options.amount) {
            createdBitmaps.add(createBitmap(options))
        }
        return createdBitmaps
    }

    /**
     * To be overridden by derived classes for the implementation of the bitmap creation.
     * Called from [createBitmaps] each time a new bitmap is created.
     */
    protected abstract fun createBitmap(options: ImageCreatorOptions): Bitmap
}