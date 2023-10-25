package com.randomimagecreator.common.generators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.configuration.Configuration

/**
 * Generates a [Bitmap] with each pixel having a random color.
 */
class PixelatedGenerator : ImageGenerator() {
    override fun createBitmap(options: Configuration): Bitmap {
        val bitmap = Bitmap.createBitmap(
            options.width,
            options.height,
            Bitmap.Config.ARGB_8888
        ).apply {
            setHasAlpha(false)
        }

        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                bitmap.setPixel(x, y, Color.parseColor(generateRandomHexColorValue()))
            }
        }

        return bitmap
    }
}
