package com.randomimagecreator

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.randomimagecreator.creators.SolidColorCreator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SolidColorCreatorTests {
    @Test
    fun createBitmaps_ReturnsNonTransparentBitmaps() {
        val bitmap = SolidColorCreator().createBitmaps()[0]
        Assert.assertFalse(bitmap.hasAlpha())
    }

    @Test
    fun createBitmaps_ReturnsBitmapsWithSolidColors() {
        val bitmap = SolidColorCreator().createBitmaps()[0]
        // Compare equality of random pixels here. It does not give 100% guarantee but is sufficient
        Assert.assertEquals(bitmap.getPixel(0, 0), bitmap.getPixel(3, 3))
    }

    @Test
    fun createBitmaps_WithASetWidth_ReturnsBitmapsWithTheGivenWidth() {
        val options = ImageCreatorOptions(width = 137)
        val bitmap = SolidColorCreator().createBitmaps(options)[0]
        Assert.assertEquals(137, bitmap.width)
    }

    @Test
    fun createBitmap_WithASetHeight_ReturnsBitmapsWithTheGivenHeight() {
        val options = ImageCreatorOptions(height = 137)
        val bitmap = SolidColorCreator().createBitmaps(options)[0]
        Assert.assertEquals(137, bitmap.height)
    }
}