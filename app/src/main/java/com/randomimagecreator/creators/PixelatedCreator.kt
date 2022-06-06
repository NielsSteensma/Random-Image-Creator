package com.randomimagecreator.creators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.common.ImageCreatorOptions

/**
 * Creates bitmaps with each pixel having a random color.
 */
class PixelatedCreator : ImageCreator() {
    override fun createBitmap(options: ImageCreatorOptions): Bitmap {
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
