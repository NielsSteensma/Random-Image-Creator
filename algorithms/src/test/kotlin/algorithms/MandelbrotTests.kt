package algorithms

import org.junit.Assert
import org.junit.Test

class MandelbrotTests {
    @Test
    fun `createBitmap, correctly returns bitmap`() {
        val bitmap = Mandelbrot(100, 100, 5).createBitmap()
        Assert.assertEquals(100, bitmap.size)
    }
}