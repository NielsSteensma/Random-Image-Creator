package com.randomimagecreator.configuration

import com.randomimagecreator.R

class ConfigurationValidator(val configuration: Configuration) {
    fun widthValidationMessage(): Int? {
        if (configuration.pattern != ImagePattern.SIERPINSKI_CARPET && configuration.width > 0) {
            return null
        }

        return if (configuration.pattern == ImagePattern.SIERPINSKI_CARPET) {
            if (configuration.width == 0) {
                R.string.image_creator_option_invalid
            }
            else if (configuration.width % 3 != 0) {
                R.string.image_creator_option_invalid_not_dividable_by_3
            } else if (configuration.width != configuration.height) {
                R.string.image_creator_option_invalid_not_equal
            } else {
                null
            }
        } else {
            R.string.image_creator_option_invalid
        }
    }

    fun heightValidationMessage(): Int? {
        if (configuration.pattern != ImagePattern.SIERPINSKI_CARPET && configuration.height > 0) {
            return null
        }

        return if (configuration.pattern == ImagePattern.SIERPINSKI_CARPET) {
            if (configuration.height == 0) {
                R.string.image_creator_option_invalid
            }
            else if (configuration.height % 3 != 0) {
                R.string.image_creator_option_invalid_not_dividable_by_3
            } else if (configuration.height != configuration.width) {
                R.string.image_creator_option_invalid_not_equal
            } else {
                null
            }
        } else {
            R.string.image_creator_option_invalid
        }
    }

    fun amountValidationMessage(): Int? {
        if (configuration.amount > 0) return null
        return R.string.image_creator_option_invalid
    }

    fun iterationsValidationMessage(): Int? {
        if (configuration.pattern != ImagePattern.MANDELBROT || configuration.iterations > 0) {
            return null
        }
        return R.string.image_creator_option_invalid
    }
}