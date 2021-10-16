package com.randomimagecreator.helpers

import org.junit.Assert.assertEquals
import org.junit.Test

class IntExtensionsTest {
    @Test
    fun parse_givenANullCharacterSequence_ShouldReturn0() {
        assertEquals(Int.parse(null), 0)
    }

    @Test
    fun parse_givenAnEmptyCharacterSequence_ShouldReturn0() {
        assertEquals(Int.parse(""), 0)
    }

    @Test
    fun parse_givenACharacterSequenceOfWhitespaces_ShouldReturn0() {
        assertEquals(Int.parse("   "), 0)
    }

    @Test
    fun parse_givenACharacterSequenceOfAnyNumber_ShouldReturnTheNumber() {
        assertEquals(Int.parse("8"), 8)
    }
}
