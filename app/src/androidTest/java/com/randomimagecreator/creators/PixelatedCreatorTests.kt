package com.randomimagecreator.creators

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.common.generators.PixelatedGenerator
import com.randomimagecreator.common.generators.SolidColorGenerator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PixelatedCreatorTests {

    @Test
    fun createBitmaps_ReturnsNonTransparentBitmaps() {
        val bitmap = SolidColorGenerator().createBitmaps(stubImageCreatorOptions())[0]
        Assert.assertFalse(bitmap.hasAlpha())
    }

    @Test
    fun createBitmaps_ReturnsBitmapsWithPixelatedColors() {
        val bitmap = PixelatedGenerator().createBitmaps(stubImageCreatorOptions())[0]
        val colorPixel1 = bitmap.getPixel(0, 0)
        val colorPixel2 = bitmap.getPixel(0, 1)
        Assert.assertNotEquals(colorPixel1, colorPixel2)
    }

    @Test
    fun createBitmaps_WithASetWidth_ReturnsBitmapsWithTheGivenWidth() {
        val options = Configuration(1, 137, 10)
        val bitmap = SolidColorGenerator().createBitmaps(options)[0]
        Assert.assertEquals(137, bitmap.width)
    }

    @Test
    fun createBitmap_WithASetHeight_ReturnsBitmapsWithTheGivenHeight() {
        val options = Configuration(1, 10, 137)
        val bitmap = SolidColorGenerator().createBitmaps(options)[0]
        Assert.assertEquals(137, bitmap.height)
    }

    private fun stubImageCreatorOptions() = Configuration(1, 10, 10)
}
