package com.randomimagecreator.common

import android.graphics.Bitmap

/**
 * Defines all possible file formats for storing images.
 */
enum class ImageFileFormat(val mimeType: String, val compressFormat: Bitmap.CompressFormat) {
    JPEG("image/jpeg", Bitmap.CompressFormat.JPEG),
    PNG("image/png", Bitmap.CompressFormat.PNG),
    @Suppress("DEPRECATION")
    WEBP("image/webp", Bitmap.CompressFormat.WEBP)
}
