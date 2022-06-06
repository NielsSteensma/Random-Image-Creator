package com.randomimagecreator.helpers

import org.junit.Assert
import org.junit.Test

class EnumExtensionsTest {

    @Test
    fun `capitalizedValuesOf returns capitalized values of the enum`() {
        val enumTestClassValues = capitalizedValuesOf<EnumTestClass>()
        Assert.assertEquals("Testvalue1", enumTestClassValues[0])
        Assert.assertEquals("Testvalue2", enumTestClassValues[1])
    }
}

@Suppress("unused")
private enum class EnumTestClass {
    TESTVALUE1,
    TESTVALUE2
}
