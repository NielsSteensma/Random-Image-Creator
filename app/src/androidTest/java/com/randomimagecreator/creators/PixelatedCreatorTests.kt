package com.randomimagecreator.creators

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.randomimagecreator.common.ImageCreatorOptions
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PixelatedCreatorTests {

    @Test
    fun createBitmaps_ReturnsNonTransparentBitmaps() {
        val bitmap = SolidColorCreator().createBitmaps(stubImageCreatorOptions())[0]
        Assert.assertFalse(bitmap.hasAlpha())
    }

    @Test
    fun createBitmaps_ReturnsBitmapsWithPixelatedColors() {
        val bitmap = PixelatedCreator().createBitmaps(stubImageCreatorOptions())[0]
        val colorOnPixel1 = bitmap.getPixel(0, 0)
        val colorOnPixel2 = bitmap.getPixel(1, 1)
        Assert.assertNotEquals(colorOnPixel1, colorOnPixel2)
    }

    @Test
    fun createBitmaps_WithASetWidth_ReturnsBitmapsWithTheGivenWidth() {
        val options = ImageCreatorOptions(1, 137, 10)
        val bitmap = SolidColorCreator().createBitmaps(options)[0]
        Assert.assertEquals(137, bitmap.width)
    }

    @Test
    fun createBitmap_WithASetHeight_ReturnsBitmapsWithTheGivenHeight() {
        val options = ImageCreatorOptions(1, 10, 137)
        val bitmap = SolidColorCreator().createBitmaps(options)[0]
        Assert.assertEquals(137, bitmap.height)
    }

    private fun stubImageCreatorOptions() = ImageCreatorOptions(1, 10, 10)
}
