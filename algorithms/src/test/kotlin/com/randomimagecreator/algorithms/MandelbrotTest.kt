package com.randomimagecreator.algorithms

import org.junit.Assert
import org.junit.Test

class MandelbrotTests {
    @Test
    fun `createBitmap, correctly returns bitmap`() {
        val bitmap = Mandelbrot(100, 100, 5, HsvToHexConverter()).createImage()
        Assert.assertEquals(100, bitmap.size)
    }

    private class HsvToHexConverter : HsvToHexConverting {
        override fun convert(hue: Float, saturation: Float, value: Float): String {
            return "#FFFFFF"
        }
    }
}
