package com.randomimagecreator.common

import android.graphics.Bitmap
import android.graphics.Color

/**
 * Wrapper class for creating Bitmaps.
 */
class BitmapCreator {
    fun create(
        bitmap: Array<String>,
        width: Int,
        height: Int
    ): Bitmap {
        return Bitmap.createBitmap(
            bitmap.map { pixel -> Color.parseColor(pixel) }.toIntArray(),
            width,
            height,
            Bitmap.Config.ARGB_8888
        ).also {
            it.setHasAlpha(false)
        }
    }
}