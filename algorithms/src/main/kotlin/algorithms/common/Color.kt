package algorithms.common

import kotlin.math.floor
import kotlin.random.Random

internal object Color {
    const val WHITE = "#FFFFFF"
    const val BLACK = "#000000"

    /**
     * Returns a randomly generated hexadecimal color code as [String].
     *
     * @implNote This method works by selecting 6 times a random character of the list of random hexadecimal
     * color codes to create the final value.
     */
    fun randomHex(): String {
        val random = Random(System.currentTimeMillis())
        val possibleHexadecimalCharacters =
            charArrayOf(
                '0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                'A',
                'B',
                'C',
                'D',
                'E',
                'F'
            )
        var color = "#"
        for (i in 0..5) {
            color += possibleHexadecimalCharacters[random.nextInt(0, 15)]
        }
        return color
    }


    /**
     * Converts provided HSV color to a hexadecimal representation.
     */
    fun hsvToHex(hue: Float, saturation: Float, value: Float): String {
        val rgbColor = hsvtoRgb(hue, saturation, value)
        return "#" + Integer.toHexString(rgbColor).substring(2)
    }

    /**
     * Converts provided HSV value to a RGB color.
     * @implNote This code is a copy of [java.awt.Color.HSBtoRGB]. We cannot use java.awt one cause
     * Android doesn't support it. Therefore we use our own copy of this part of their code.
     */
    private fun hsvtoRgb(hue: Float, saturation: Float, brightness: Float): Int {
        var r = 0
        var g = 0
        var b = 0
        if (saturation == 0f) {
            b = (brightness * 255.0f + 0.5f).toInt()
            g = b
            r = g
        } else {
            val h = (hue - floor(hue.toDouble()).toFloat()) * 6.0f
            val f = h - floor(h.toDouble()).toFloat()
            val p = brightness * (1.0f - saturation)
            val q = brightness * (1.0f - saturation * f)
            val t = brightness * (1.0f - saturation * (1.0f - f))
            when (h.toInt()) {
                0 -> {
                    r = (brightness * 255.0f + 0.5f).toInt()
                    g = (t * 255.0f + 0.5f).toInt()
                    b = (p * 255.0f + 0.5f).toInt()
                }

                1 -> {
                    r = (q * 255.0f + 0.5f).toInt()
                    g = (brightness * 255.0f + 0.5f).toInt()
                    b = (p * 255.0f + 0.5f).toInt()
                }

                2 -> {
                    r = (p * 255.0f + 0.5f).toInt()
                    g = (brightness * 255.0f + 0.5f).toInt()
                    b = (t * 255.0f + 0.5f).toInt()
                }

                3 -> {
                    r = (p * 255.0f + 0.5f).toInt()
                    g = (q * 255.0f + 0.5f).toInt()
                    b = (brightness * 255.0f + 0.5f).toInt()
                }

                4 -> {
                    r = (t * 255.0f + 0.5f).toInt()
                    g = (p * 255.0f + 0.5f).toInt()
                    b = (brightness * 255.0f + 0.5f).toInt()
                }

                5 -> {
                    r = (brightness * 255.0f + 0.5f).toInt()
                    g = (p * 255.0f + 0.5f).toInt()
                    b = (q * 255.0f + 0.5f).toInt()
                }
            }
        }
        return -0x1000000 or (r shl 16) or (g shl 8) or (b shl 0)
    }
}