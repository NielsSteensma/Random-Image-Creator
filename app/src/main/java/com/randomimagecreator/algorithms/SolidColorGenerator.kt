package com.randomimagecreator.algorithms

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.ImageCreatorOptions
import kotlin.random.Random

/**
 * Generates a bitmap with a random solid color.
 *
 * Note: The algorithm works by keeping a list of all 16 possible hex characters. Then it selects
 * 6 times a random value of this list to construct a random hex color code.
 *
 * The bitmap is then created based on this random color code.
 */
class SolidColorGenerator : ImageGenerator() {
    /**
     * Array of all possible hexadecimal characters
     */
    private val possibleHexadecimalCharacters =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * Returns a bitmap with a randomly chosen solid color
     *
     * @param options   Any custom options to set for the generated bitmap.
     */
    override fun generateBitmap(options: ImageCreatorOptions?) = generateSingleBitmap(options)
    override fun generateSingleBitmap(options: ImageCreatorOptions?): Bitmap {
        val color = Color.parseColor(generateRandomHexColorValue())

        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
            setHasAlpha(false)
        }

        for (x in 0..99) {
            for (y in 0..99) {
                bitmap.setPixel(x, y, color)
            }
        }

        return bitmap
    }

    /**
     * Returns a randomly generated hexadecimal string.
     */
    private fun generateRandomHexColorValue(): String {
        var color = "#"
        for (i in 0..5) {
            color += possibleHexadecimalCharacters[Random.nextInt(0, 15)]
        }
        return color
    }
}