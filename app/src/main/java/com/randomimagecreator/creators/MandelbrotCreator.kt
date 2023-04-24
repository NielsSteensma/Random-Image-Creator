package com.randomimagecreator.creators

import android.graphics.Bitmap
import android.graphics.Color
import com.randomimagecreator.common.ImageCreatorOptions
import kotlin.math.sqrt

/**
 * Amount of iterations to perform to check if complex number is in Mandelbrot set.
 *
 * Value is tradeoff between quality ( higher ) and performance ( lower ).
 */
private const val ITERATIONS = 200

/**
 * Max range complex number can be from origin to be part of Mandelbrot set.
 */
private const val ESCAPE_RADIUS = 2

/**
 * Creates [Bitmap] based on Mandelbrot algorithm.
 */
class MandelbrotCreator : ImageCreator() {
    private val plotXMin: Double = -2.0
    private val plotXMax: Double = 1.0
    private val plotYMin: Double = -1.0
    private val plotYMax: Double = 1.0

    override fun createBitmap(options: ImageCreatorOptions): Bitmap {
        val bitmap =
            Bitmap.createBitmap(options.width, options.height, Bitmap.Config.ARGB_8888).apply {
                setHasAlpha(false)
            }

        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val complex = pixelCoordinatesToComplexCoordinates(options, x, y)
                val iterations = mandelbrot(complex)
                val color = if (iterations == ITERATIONS) Color.BLACK else Color.WHITE
                bitmap.setPixel(x, y, color)
            }
        }

        return bitmap
    }

    /**
     * Converts pixel coordinates to complex number taking into account scale factor.
     */
    private fun pixelCoordinatesToComplexCoordinates(
        options: ImageCreatorOptions,
        pixelX: Int,
        pixelY: Int
    ): Complex {
        val complexX = plotXMin + (plotXMax - plotXMin) * pixelX / options.width
        val complexY = plotYMin + (plotYMax - plotYMin) * pixelY / options.height
        return Complex(complexX, complexY)
    }

    /**
     * Performs Mandelbrot algorithm for the given [Complex].
     *
     * @return [Int] with number of performed iterations.
     */
    private fun mandelbrot(complex: Complex): Int {
        var iteratedValue = Complex(0.0, 0.0)
        // Add iteratedValue to itself and perform Mandelbrot calculation on it
        for (i in 0..ITERATIONS) {
            // if iteration made iteratedValue go outside escape radius we argue complex number is
            // infinite, thus no part of Mandelbrot set.
            if (iteratedValue.abs() > ESCAPE_RADIUS) {
                return i
            }
            iteratedValue = iteratedValue * iteratedValue + complex
        }
        return ITERATIONS
    }
}

/**
 * Represents a complex number with a real and imaginary component.
 *
 * @param real real component of complex number.
 * @param imaginary imaginary component of complex number.
 */
private data class Complex(val real: Double, val imaginary: Double) {
    /**
     * Calculates distance between origin(0,0) and [Complex] number.
     * @return [Double] with distance between origin and [Complex] number.
     */
    fun abs() = sqrt((real * real) + (imaginary * imaginary))

    /**
     * Sum two [Complex] numbers.
     * @return [Complex] with result of sum.
     */
    operator fun plus(complex: Complex) =
        Complex(real + complex.real, imaginary + complex.imaginary)

    /**
     * Multiplies two [Complex] numbers
     * @return [Complex] with result of multiplication.
     */
    operator fun times(complex: Complex) =
        Complex(
            real * complex.real - imaginary * complex.imaginary,
            real * complex.imaginary + imaginary * complex.real
        )
}
