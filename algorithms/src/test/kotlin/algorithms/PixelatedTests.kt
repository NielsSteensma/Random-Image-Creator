package algorithms

import com.randomimagecreator.algorithms.Pixelated
import org.junit.Assert
import org.junit.Test

class PixelatedTests {
    @Test
    fun `createBitmap, correctly returns bitmap with pixels having different colors`() {
        val bitmap = Pixelated.createImage(100, 100)
        val firstPixelColor = bitmap[0]
        Assert.assertTrue(bitmap.any { pixel -> !pixel.contentEquals(firstPixelColor) })
    }
}