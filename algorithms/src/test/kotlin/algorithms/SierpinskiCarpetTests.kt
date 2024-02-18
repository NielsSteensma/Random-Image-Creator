package algorithms

import org.junit.Assert
import org.junit.Test

class SierpinskiCarpetTests {
    @Test
    fun `createBitmap, given valid configuration, correctly returns bitmap`() {
        val bitmap = SierpinskiCarpet(300, 300).createBitmap()
        val width = bitmap[0].size
        Assert.assertEquals(300, width)
    }

    @Test
    fun `createBitmap, given width that is not dividable by 3, throws IllegalArgumentException`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            SierpinskiCarpet(100, 100).createBitmap()
        }
    }

    @Test
    fun `createBitmap, given height that is not dividable by 3, throws IllegalArgumentException`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            SierpinskiCarpet(100, 100).createBitmap()
        }
    }

    @Test
    fun `createBitmap, given width and height that are not equal, throws IllegalArgumentException`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            SierpinskiCarpet(100, 100).createBitmap()
        }
    }
}