package com.randomimagecreator.creators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.common.ImageCreatorOptions

/**
 * Creates bitmaps with a random solid color.
 */
class SolidColorCreator : ImageCreator() {
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
}
