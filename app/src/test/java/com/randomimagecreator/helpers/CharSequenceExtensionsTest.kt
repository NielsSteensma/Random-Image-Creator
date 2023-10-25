package com.randomimagecreator.helpers

import com.randomimagecreator.common.extensions.toInt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CharSequenceExtensionsTest {
    @Test
    fun `safeParse given an empty character sequence returns null`() {
        assertNull("".toInt())
    }

    @Test
    fun `safeParse given a character sequence of whitespaces returns null`() {
        assertNull("   ".toInt())
    }

    @Test
    fun `safeParse given a character sequence of any number returns the number`() {
        assertEquals(7, "7".toInt())
    }

    @Test
    fun `safeParse given an non parse-able character returns null`() {
        assertNull("A".toInt())
    }
}
