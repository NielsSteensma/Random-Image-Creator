package com.randomimagecreator.configuration.validation

data class ConfigurationValidationResult(
    val widthValidationMessage: Int?,
    val heightValidationMessage: Int?,
    val amountValidationMessage: Int?,
    val iterationsValidationMessage: Int?
) {
    val isValid: Boolean
        get() {
            return widthValidationMessage == null &&
                    heightValidationMessage == null &&
                    amountValidationMessage == null &&
                    iterationsValidationMessage == null
        }

    companion object {
        fun valid(): ConfigurationValidationResult {
            return ConfigurationValidationResult(null, null, null, null)
        }
    }
}