package com.randomimagecreator

/**
 * Holds all user-selectable options of the image creator screen
 */
data class ImageCreatorOptions(
        val amount: Int,
        val format: ImageCreatorFormat,
        val pattern: ImageCreatorPattern,
        val storageLocation: Any
)
