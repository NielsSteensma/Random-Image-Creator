package com.randomimagecreator.configuration

data class ConfigurationValidatorResult(
    val widthWarningResourceId: Int?,
    val heightWarningResourceId: Int?,
    val amountWarningResourceId: Int?,
    val iterationsWarningResourceId: Int?
) {
    val isValid: Boolean = widthWarningResourceId == null &&
            heightWarningResourceId == null &&
            amountWarningResourceId == null &&
            iterationsWarningResourceId == null
}