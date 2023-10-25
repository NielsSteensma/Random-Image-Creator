package com.randomimagecreator.common

import android.graphics.Bitmap
import com.randomimagecreator.configuration.ImageFileFormat
import org.junit.Assert
import org.junit.Test

class ImageFileFormatTest {
    @Test
    fun `ensure JPEG file format mime type is correct`() {
        Assert.assertEquals("image/jpeg", ImageFileFormat.JPEG.mimeType)
    }

    @Test
    fun `ensure JPEG file format compress type is correct`() {
        Assert.assertEquals(Bitmap.CompressFormat.JPEG, ImageFileFormat.JPEG.compressFormat)
    }

    @Test
    fun `ensure PNG file format mime type is correct`() {
        Assert.assertEquals("image/png", ImageFileFormat.PNG.mimeType)
    }

    @Test
    fun `ensure PNG file format compress type is correct`() {
        Assert.assertEquals(Bitmap.CompressFormat.PNG, ImageFileFormat.PNG.compressFormat)
    }

    @Test
    fun `ensure WEBP file format mime type is correct`() {
        Assert.assertEquals("image/webp", ImageFileFormat.WEBP.mimeType)
    }

    @Test
    fun `ensure WEBP file format compress type is correct`() {
        @Suppress("DEPRECATION")
        Assert.assertEquals(
            Bitmap.CompressFormat.WEBP,
            ImageFileFormat.WEBP.compressFormat
        )
    }
}
