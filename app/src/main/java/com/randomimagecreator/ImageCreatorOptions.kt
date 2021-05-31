package com.randomimagecreator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Holds all user-selectable options of the image creator screen
 */
@Parcelize
data class ImageCreatorOptions(
    val amount: Int,
    val width: Int,
    val height: Int,
    val storageDirectory: String
) : Parcelable
