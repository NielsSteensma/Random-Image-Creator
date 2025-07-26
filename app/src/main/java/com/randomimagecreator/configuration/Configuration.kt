package com.randomimagecreator.configuration

import android.net.Uri

/**
 * Holds all user-selectable options of the image creator screen.
 */
data class Configuration(
    var amount: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    /**
     * Amount of iterations to perform to check if complex number is in Mandelbrot set.
     *
     * Default value is tradeoff between quality ( higher ) and performance ( lower ).
     */
    var iterations: Int = 100,
    var pattern: ImagePattern = ImagePattern.SOLID,
    var format: ImageFileFormat = ImageFileFormat.JPEG,
    var saveDirectory: Uri? = null
)
