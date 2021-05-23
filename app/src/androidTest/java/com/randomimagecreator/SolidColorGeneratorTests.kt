package com.randomimagecreator

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.randomimagecreator.algorithms.SolidColorGenerator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SolidColorGeneratorTests {
    @Test
    fun generateBitmap_ReturnsANonTransparentBitmap() {
        val bitmap = SolidColorGenerator().generateBitmap()
        Assert.assertFalse(bitmap.hasAlpha())
    }

    @Test
    fun generateBitmap_ReturnsABitmapWithSolidColors() {
        val bitmap = SolidColorGenerator().generateBitmap()
        // Compare equality of random pixels here. It does not give 100% guarantee but is sufficient
        Assert.assertEquals(bitmap.getPixel(0, 0), bitmap.getPixel(3, 3))
    }
}