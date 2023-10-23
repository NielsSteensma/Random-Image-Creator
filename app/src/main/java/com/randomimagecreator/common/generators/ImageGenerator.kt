package com.randomimagecreator.common.generators

import android.graphics.Bitmap
import com.randomimagecreator.configuration.Configuration
import kotlin.random.Random

/**
 *  Base class for derived image generation classes.
 *
 *  Derived classes can call [generateRandomHexColorValue] to create a random hexadecimal color code.
 */
abstract class ImageGenerator {
    private val random = Random(System.currentTimeMillis())
    private val possibleHexadecimalCharacters =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * Returns an array of randomly created bitmaps with the generation algorithm of the derived class.
     *
     * @param options   Any custom options to set for the generated bitmaps.
     */
    fun createBitmaps(options: Configuration): MutableList<Bitmap> {
        val createdBitmaps = mutableListOf<Bitmap>()
        for (i in 0 until options.amount) {
            createdBitmaps.add(createBitmap(options))
        }
        return createdBitmaps
    }

    /**
     * Returns a randomly generated hexadecimal color code as [String].
     *
     * This method works by selecting 6 times a random character of the list of random hexadecimal
     * color codes to create the final value.
     */
    protected fun generateRandomHexColorValue(): String {
        var color = "#"
        for (i in 0..5) {
            color += possibleHexadecimalCharacters[random.nextInt(0, 15)]
        }
        return color
    }

    /**
     * To be overridden by derived classes for the implementation of the bitmap creation.
     * Called from [createBitmaps] each time a new bitmap is created.
     */
    protected abstract fun createBitmap(options: Configuration): Bitmap
}
