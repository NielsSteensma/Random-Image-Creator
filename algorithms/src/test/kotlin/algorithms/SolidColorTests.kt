package algorithms

import com.randomimagecreator.algorithms.SolidColor
import org.junit.Assert
import org.junit.Test

class SolidColorTests {
    @Test
    fun `createBitmap, correctly returns bitmap with one solid color`() {
        val bitmap = SolidColor(100, 100).createImage()
        val firstPixelColor = bitmap[0]
        Assert.assertFalse(bitmap.any { pixel -> !pixel.contentEquals(firstPixelColor) })
    }
}