package com.randomimagecreator.algorithms.common

import kotlin.math.sqrt

/**
 * Represents a complex number with a real and imaginary component.
 *
 * @param real real component of complex number.
 * @param imaginary imaginary component of complex number.
 */
internal data class Complex(val real: Double, val imaginary: Double) {
    /**
     * Calculates distance between origin(0,0) and [Complex] number.
     * @return [Double] with distance between origin and [Complex] number.
     */
    fun abs() = sqrt((real * real) + (imaginary * imaginary))

    /**
     * Sums two [Complex] numbers.
     * @return [Complex] with result of sum.
     */
    operator fun plus(complex: Complex) =
        Complex(real + complex.real, imaginary + complex.imaginary)

    /**
     * Multiplies two [Complex] numbers.
     * @return [Complex] with result of multiplication.
     */
    operator fun times(complex: Complex) =
        Complex(
            real * complex.real - imaginary * complex.imaginary,
            real * complex.imaginary + imaginary * complex.real
        )
}
