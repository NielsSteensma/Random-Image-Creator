package com.randomimagecreator.helpers

import org.junit.Assert.assertEquals
import org.junit.Test

class IntExtensionsTest {
    @Test
    fun `parse given a null character sequence returns 0`() {
        assertEquals(Int.parse(null), 0)
    }

    @Test
    fun `parse given an empty character sequence returns 0`() {
        assertEquals(Int.parse(""), 0)
    }

    @Test
    fun `parse given a character sequence of whitespaces returns 0`() {
        assertEquals(Int.parse("   "), 0)
    }

    @Test
    fun `parse given a character sequence of any number returns the number`() {
        assertEquals(Int.parse("7"), 7)
    }
}
