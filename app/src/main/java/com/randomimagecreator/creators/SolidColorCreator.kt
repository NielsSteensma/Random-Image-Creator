package com.randomimagecreator.creators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.ImageCreatorOptions
import kotlin.random.Random

/**
 * Creates bitmaps with a random solid color.
 *
 * Note: The algorithm works by keeping a list of all 16 possible hex characters. Then it selects
 * 6 times a random value of this list to construct a random hex color code.
 *
 * The bitmap is then created based on this random color code.
 */
class SolidColorCreator : ImageCreator() {
    /**
     * Array of all possible hexadecimal characters.
     */
    private val possibleHexadecimalCharacters =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    override fun createBitmap(options: ImageCreatorOptions): Bitmap {
        val color = Color.parseColor(generateRandomHexColorValue())

        val bitmap = Bitmap.createBitmap(
            options.width,
            options.height,
            Bitmap.Config.ARGB_8888
        ).apply {
            setHasAlpha(false)
        }

        for (x in 0 until options.width) {
            for (y in 0 until options.height) {
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
