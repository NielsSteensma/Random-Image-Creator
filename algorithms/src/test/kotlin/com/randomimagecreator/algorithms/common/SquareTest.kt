package com.randomimagecreator.algorithms.common

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SquareTest {

    @Test
    fun `init, accepts equal width and height`() {
        try {
            Square(0, 0, 100, 100)
        } catch (exception: IllegalArgumentException) {
            Assert.assertTrue(false)
        }
    }

    @Test
    fun `init, throws exception when width and height not equal`() {
        assertThrows(IllegalArgumentException::class.java) {
            Square(0, 0, 100, 600)
        }
    }

    @Test
    fun `divideInNineSubSquares, contains correct positions in linked hash set`() {
        val square = Square(0,0, 300, 300)
        val subSquares = square.divideInNineSubSquares()
        // Left
        assertEquals(Square(0,0,100,100), subSquares.elementAt(0))
        assertEquals(Square(0,100,100,200), subSquares.elementAt(1))
        assertEquals(Square(0,200,100,300), subSquares.elementAt(2))
        // Middle
        assertEquals(Square(100,0,200,100), subSquares.elementAt(3))
        assertEquals(Square(100,100,200,200), subSquares.elementAt(4))
        assertEquals(Square(100,200,200,300), subSquares.elementAt(5))
        // Right
        assertEquals(Square(200,0,300,100), subSquares.elementAt(6))
        assertEquals(Square(200,100,300,200), subSquares.elementAt(7))
        assertEquals(Square(200,200,300,300), subSquares.elementAt(8))
    }
}