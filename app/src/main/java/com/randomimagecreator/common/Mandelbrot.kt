package com.randomimagecreator.common

import android.graphics.Color
import com.randomimagecreator.algorithms.HsvToHexConverting

object HsvToHexConverter : HsvToHexConverting {
    @OptIn(ExperimentalStdlibApi::class)
    override fun convert(hue: Float, saturation: Float, value: Float): String {
        val format = HexFormat {
            upperCase = true
            number.prefix = "#"
            number.removeLeadingZeros = true
        }
        val floatArray = floatArrayOf(hue, saturation, value)
        return Color.HSVToColor(floatArray).toHexString(format)
    }
}
