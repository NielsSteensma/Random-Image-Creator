package algorithms

import com.randomimagecreator.algorithms.HsvToHexConverting
import com.randomimagecreator.algorithms.Mandelbrot
import org.junit.Assert
import org.junit.Test

class MandelbrotTests {
    @Test
    fun `createBitmap, correctly returns bitmap`() {
        val bitmap = Mandelbrot(100, 100, 5, HsvToHexConverter()).createImage()
        Assert.assertEquals(100, bitmap.size)
    }
}

internal class HsvToHexConverter : HsvToHexConverting {
    override fun convert(hue: Float, saturation: Float, value: Float): String {
        return "#FFFFFF"
    }
}