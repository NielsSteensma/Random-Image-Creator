package com.randomimagecreator.algorithms

import com.randomimagecreator.algorithms.common.Color
import com.randomimagecreator.algorithms.common.Complex

/**
 * Max range complex number can be from origin to be part of Mandelbrot set.
 */
private const val ESCAPE_RADIUS = 2

/**
 * Algorithm for creating an image based on Mandelbrot.
 */
class Mandelbrot(
    private val width: Int,
    private val height: Int,
    private val iterations: Int
) : ImageCreating {
    private val plot = Plot()

    override fun createImage(): Array<Array<String>> {
        val image = Array(width) { Array(height) { "" } }

        for (x in 0 until width) {
            for (y in 0 until height) {
                val maxIterations = iterations
                val complex = pixelCoordinatesToComplexCoordinates(width, height, x, y)
                val iterations = mandelbrot(maxIterations, complex)
                val color = color(maxIterations, iterations)
                image[x][y] = color
            }
        }

        return image
    }

    /**
     * Returns color based on provided [performedIterations].
     * If [performedIterations] matches [maxIterations], black will be returned.
     * If not matching, color will be based on max reached iterations.
     */
    private fun color(maxIterations: Int, performedIterations: Int): String {
        if (performedIterations == maxIterations) {
            return Color.BLACK
        }

        val hue = 255f * performedIterations / maxIterations
        val saturation = 1f
        val value = 1f
        return Color.hsvToHex(hue, saturation, value)
    }

    /**
     * Converts pixel coordinates to complex number taking into account scale factor.
     * @return [Complex] with coordinates.
     */
    private fun pixelCoordinatesToComplexCoordinates(
        width: Int,
        height: Int,
        pixelX: Int,
        pixelY: Int
    ): Complex {
        val complexX = plot.xMin + (plot.xMax - plot.xMin) * pixelX / width
        val complexY = plot.yMin + (plot.yMax - plot.yMin) * pixelY / height
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
 * Represents a plot for [Mandelbrot].
 * Increasing/decreasing values changes zoom level.
 */
private data class Plot(
    val xMin: Double = -2.0,
    val xMax: Double = 1.0,
    val yMin: Double = -1.0,
    val yMax: Double = 1.0
)