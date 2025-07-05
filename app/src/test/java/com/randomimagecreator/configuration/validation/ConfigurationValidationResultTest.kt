package com.randomimagecreator.configuration.validation

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ConfigurationValidationResultTest {
    @Test
    fun `isValid, when has no validation messages, returns true`() {
        val configurationValidationResult = ConfigurationValidationResult.valid()
        assertEquals(true, configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has widthValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = 1,
            heightValidationMessage = null,
            amountValidationMessage = null,
            iterationsValidationMessage = null
        )
        assertEquals(false, configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has heightValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = null,
            heightValidationMessage = 1,
            amountValidationMessage = null,
            iterationsValidationMessage = null
        )
        assertEquals(false, configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has amountValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = null,
            heightValidationMessage = null,
            amountValidationMessage = 1,
            iterationsValidationMessage = null
        )
        assertEquals(false, configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has iterationValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = null,
            heightValidationMessage = null,
            amountValidationMessage = null,
            iterationsValidationMessage = 1
        )
        assertEquals(false, configurationValidationResult.isValid)
    }
}