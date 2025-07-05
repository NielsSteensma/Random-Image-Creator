package com.randomimagecreator.configuration.validation

import com.randomimagecreator.R
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern

object ConfigurationValidator {

    fun validate(configuration: Configuration): ConfigurationValidationResult {
        return when (configuration.pattern) {
            ImagePattern.SOLID, ImagePattern.PIXELATED -> {
                ConfigurationValidationResult(
                    widthValidationMessage = validateWidthIsNotZero(configuration),
                    heightValidationMessage = validateHeightIsNotZero(configuration),
                    iterationsValidationMessage = null,
                    amountValidationMessage = validateAmountIsNotZero(configuration)
                )
            }

            ImagePattern.MANDELBROT -> {
                ConfigurationValidationResult(
                    widthValidationMessage = validateWidthIsNotZero(configuration),
                    heightValidationMessage = validateHeightIsNotZero(configuration),
                    iterationsValidationMessage = validateIterationsIsNotZero(configuration),
                    amountValidationMessage = validateAmountIsNotZero(configuration)
                )
            }

            ImagePattern.SIERPINSKI_CARPET -> {
                ConfigurationValidationResult(
                    widthValidationMessage = validateWidthIsNotZero(configuration)
                        ?: validateWidthAndHeightAreEqual(configuration)
                        ?: validateWidthIsDividableByThree(configuration),
                    heightValidationMessage = validateHeightIsNotZero(configuration),
                    iterationsValidationMessage = validateIterationsIsNotZero(configuration),
                    amountValidationMessage = validateAmountIsNotZero(configuration)
                )
            }
        }
    }

    private fun validateAmountIsNotZero(configuration: Configuration): Int? {
        return if (configuration.amount == 0) R.string.image_creator_option_invalid else null
    }

    private fun validateWidthIsNotZero(configuration: Configuration): Int? {
        return if (configuration.width == 0) R.string.image_creator_option_invalid else null
    }

    private fun validateWidthAndHeightAreEqual(configuration: Configuration): Int? {
        return if (configuration.width != configuration.height) {
            R.string.image_creator_option_invalid_not_equal
        } else {
            null
        }
    }

    private fun validateWidthIsDividableByThree(configuration: Configuration): Int? {
        return if (configuration.height % 3 != 0) {
            R.string.image_creator_option_invalid_not_dividable_by_3
        } else {
            null
        }
    }

    private fun validateHeightIsNotZero(configuration: Configuration): Int? {
        return if (configuration.height == 0) R.string.image_creator_option_invalid else null
    }

    private fun validateIterationsIsNotZero(configuration: Configuration): Int? {
        return if (configuration.iterations == 0) R.string.image_creator_option_invalid else null
    }
}