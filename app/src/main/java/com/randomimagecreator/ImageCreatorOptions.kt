package com.randomimagecreator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Holds all user-selectable options of the image creator screen.
 */
@Parcelize
data class ImageCreatorOptions(
    var amount: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
) : Parcelable
{
    /**
     * Returns a boolean indicating if the given set of options is valid.
     */
    fun isValid() = amount != 0 && width != 0 && height != 0
}
