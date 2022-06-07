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

    @Test
    fun `capitalized returns capitalize value of the enum`() {
        Assert.assertEquals("Testvalue1", EnumTestClass.TESTVALUE1.capitalized())
    }
}

@Suppress("unused")
private enum class EnumTestClass {
    TESTVALUE1,
    TESTVALUE2
}
