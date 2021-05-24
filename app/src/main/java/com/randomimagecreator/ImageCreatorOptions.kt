package com.randomimagecreator

import android.net.Uri

/**
 * Holds all user-selectable options of the image creator screen
 */
data class ImageCreatorOptions(
    val amount: Int = 1,
    val width: Int = 100,
    val height: Int = 100,
    val format: ImageCreatorFormat = ImageCreatorFormat(""),
    val pattern: ImageCreatorPattern = ImageCreatorPattern(""),
    val storageLocation: Uri = Uri.EMPTY
)
