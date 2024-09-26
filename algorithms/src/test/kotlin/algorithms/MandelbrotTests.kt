package algorithms

import com.randomimagecreator.algorithms.Mandelbrot
import com.randomimagecreator.algorithms.common.HsvToHexConverting
import org.junit.Assert
import org.junit.Test

class MandelbrotTests {
    @Test
    fun `createBitmap, correctly returns bitmap`() {
        val bitmap = Mandelbrot.createImage(100, 100, 5, HsvToHexConverter())
        Assert.assertEquals(100, bitmap.size)
    }
}

internal class HsvToHexConverter : HsvToHexConverting {
    override fun convert(hue: Float, saturation: Float, value: Float): String {
        return "#FFFFFF"
    }
}