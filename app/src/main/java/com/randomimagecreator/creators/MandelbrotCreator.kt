package com.randomimagecreator.creators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.common.Complex
import com.randomimagecreator.common.ImageCreatorOptions

/**
 * Max range complex number can be from origin to be part of Mandelbrot set.
 */
private const val ESCAPE_RADIUS = 2

/**
 * Creates [Bitmap] based on Mandelbrot algorithm.
 */
class MandelbrotCreator : ImageCreator() {
    private val plot = Plot()

    override fun createBitmap(options: ImageCreatorOptions): Bitmap {
        val bitmap =
            Bitmap.createBitmap(options.width, options.height, Bitmap.Config.ARGB_8888).apply {
                setHasAlpha(false)
            }

        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val maxIterations = options.iterations
                val complex = pixelCoordinatesToComplexCoordinates(options, x, y)
                val iterations = mandelbrot(maxIterations, complex)
                val color = color(maxIterations, iterations)
                bitmap.setPixel(x, y, color)
            }
        }

        return bitmap
    }

    /**
     * Returns color based on provided [performedIterations].
     * If [performedIterations] matches [maxIterations], black will be returned.
     * If not matching, color will be based on max reached iterations.
     */
    private fun color(maxIterations: Int, performedIterations: Int): Int {
        if (performedIterations == maxIterations) {
            return Color.BLACK
        }

        val hue = 255f * performedIterations / maxIterations
        val saturation = 1f
        val value = 1f
        val floatArray = floatArrayOf(hue, saturation, value)
        return Color.HSVToColor(floatArray)
    }

    /**
     * Converts pixel coordinates to complex number taking into account scale factor.
     * @return [Complex] with coordinates.
     */
    private fun pixelCoordinatesToComplexCoordinates(
        options: ImageCreatorOptions,
        pixelX: Int,
        pixelY: Int
    ): Complex {
        val complexX = plot.xMin + (plot.xMax - plot.xMin) * pixelX / options.width
        val complexY = plot.yMin + (plot.yMax - plot.yMin) * pixelY / options.height
        return Complex(complexX, complexY)
    }

    /**
     * Performs Mandelbrot algorithm for the given [Complex].
     *
     * @return [Int] with number of performed iterations.
     */
    private fun mandelbrot(maxIterations: Int, complex: Complex): Int {
        var iteratedValue = Complex(0.0, 0.0)
        for (i in 0..maxIterations) {
            // if new iteration causes iteratedValue to go outside escape radius we argue
            // complex number is infinite, thus no part of Mandelbrot set.
            if (iteratedValue.abs() > ESCAPE_RADIUS) {
                return i
            }
            iteratedValue = iteratedValue * iteratedValue + complex
        }
        return maxIterations
    }
}

/**
 * Represents a plot for [MandelbrotCreator].
 * Increasing/decreasing values changes zoom level.
 */
private data class Plot(
    val xMin: Double = -2.0,
    val xMax: Double = 1.0,
    val yMin: Double = -1.0,
    val yMax: Double = 1.0
)