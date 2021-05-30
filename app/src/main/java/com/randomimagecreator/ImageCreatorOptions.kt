package com.randomimagecreator

import android.os.Parcelable

/**
 * Holds all user-selectable options of the image creator screen
 */
@kotlinx.parcelize.Parcelize
data class ImageCreatorOptions(
    val amount: Int,
    val width: Int,
    val height: Int,
    val storageDirectory: String
) : Parcelable
