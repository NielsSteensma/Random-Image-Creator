package com.randomimagecreator.configuration.validation

import org.junit.Assert
import org.junit.Test

class ConfigurationValidationResultTest {
    @Test
    fun `isValid, when has no validation messages, returns true`() {
        val configurationValidationResult = ConfigurationValidationResult.valid()
        Assert.assertTrue(configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has widthValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = 1,
            heightValidationMessage = null,
            amountValidationMessage = null,
            iterationsValidationMessage = null
        )
        Assert.assertFalse(configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has heightValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = null,
            heightValidationMessage = 1,
            amountValidationMessage = null,
            iterationsValidationMessage = null
        )
        Assert.assertFalse(configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has amountValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = null,
            heightValidationMessage = null,
            amountValidationMessage = 1,
            iterationsValidationMessage = null
        )
        Assert.assertFalse(configurationValidationResult.isValid)
    }

    @Test
    fun `isValid, when has iterationValidationMessage, returns false`() {
        val configurationValidationResult = ConfigurationValidationResult(
            widthValidationMessage = null,
            heightValidationMessage = null,
            amountValidationMessage = null,
            iterationsValidationMessage = 1
        )
        Assert.assertFalse(configurationValidationResult.isValid)
    }
}