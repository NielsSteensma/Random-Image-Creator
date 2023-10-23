package com.randomimagecreator.common.generators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.configuration.Configuration

/**
 * Generates a [Bitmap] with a random solid color.
 */
class SolidColorGenerator : ImageGenerator() {
    override fun createBitmap(options: Configuration): Bitmap {
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
}
