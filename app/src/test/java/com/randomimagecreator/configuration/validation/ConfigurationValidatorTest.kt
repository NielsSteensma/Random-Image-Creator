package com.randomimagecreator.configuration.validation

import com.randomimagecreator.R
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern
import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Test

class ConfigurationValidatorTest {

    // region Solid image pattern tests
    @Test
    fun `validator for solid image pattern, when amount, width and height are not zero, returns valid`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 100,
                pattern = ImagePattern.SOLID
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        Assert.assertTrue(validationResult.isValid)
    }

    @Test
    fun `validator for solid image pattern, when amount is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 0,
                pattern = ImagePattern.SOLID
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.amountValidationMessage
        )
    }

    @Test
    fun `validator for solid image pattern, when width is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 0,
                height = 100,
                amount = 10,
                pattern = ImagePattern.SOLID
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.widthValidationMessage
        )
    }

    @Test
    fun `validator for solid image pattern, when height is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 0,
                amount = 10,
                pattern = ImagePattern.SOLID
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.heightValidationMessage
        )
    }
    // endregion

    // region Pixelated image pattern tests
    @Test
    fun `validator for pixelated image pattern, when amount, width and height are not zero, returns valid`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 100,
                pattern = ImagePattern.PIXELATED
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        Assert.assertTrue(validationResult.isValid)
    }

    @Test
    fun `validator for pixelated image pattern, when amount is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 0,
                pattern = ImagePattern.PIXELATED
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.amountValidationMessage
        )
    }

    @Test
    fun `validator for pixelated image pattern, when width is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 0,
                height = 100,
                amount = 10,
                pattern = ImagePattern.PIXELATED
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.widthValidationMessage
        )
    }

    @Test
    fun `validator for pixelated image pattern, when height is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 0,
                amount = 10,
                pattern = ImagePattern.PIXELATED
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.heightValidationMessage
        )
    }
    // endregion

    // region Mandelbrot image pattern tests
    @Test
    fun `validator for mandelbrot image pattern, when amount, width, height and iterations are not zero, returns valid`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 100,
                iterations = 100,
                pattern = ImagePattern.MANDELBROT
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        Assert.assertTrue(validationResult.isValid)
    }

    @Test
    fun `validator for mandelbrot image pattern, when amount is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 0,
                iterations = 100,
                pattern = ImagePattern.MANDELBROT
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.amountValidationMessage
        )
    }

    @Test
    fun `validator for mandelbrot image pattern, when width is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 0,
                height = 100,
                amount = 10,
                iterations = 100,
                pattern = ImagePattern.MANDELBROT
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.widthValidationMessage
        )
    }

    @Test
    fun `validator for mandelbrot image pattern, when height is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 0,
                amount = 10,
                iterations = 100,
                pattern = ImagePattern.MANDELBROT
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.heightValidationMessage
        )
    }

    @Test
    fun `validator for mandelbrot image pattern, when iterations is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 10,
                iterations = 0,
                pattern = ImagePattern.MANDELBROT
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.iterationsValidationMessage
        )
    }
    // endregion

    // region Sierpinski image pattern tests
    @Test
    fun `validator for sierpinski image pattern, when amount, width and height are valid, returns valid`() {
        val configuration =
            Configuration(
                width = 300,
                height = 300,
                amount = 10,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        Assert.assertTrue(validationResult.isValid)
    }

    @Test
    fun `validator for sierpinski image pattern, when amount is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 100,
                amount = 0,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.amountValidationMessage
        )
    }

    @Test
    fun `validator for sierpinski image pattern, when width is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 0,
                height = 100,
                amount = 10,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.widthValidationMessage
        )
    }

    @Test
    fun `validator for sierpinski image pattern, when height is zero, returns validation warning`() {
        val configuration =
            Configuration(
                width = 100,
                height = 0,
                amount = 10,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validationResult.heightValidationMessage
        )
    }

    @Test
    fun `validator for sierpinski image pattern, when width and height are not equal, returns validation warning`() {
        val configuration =
            Configuration(
                width = 300,
                height = 600,
                amount = 10,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid_not_equal,
            validationResult.widthValidationMessage
        )
    }

    @Test
    fun `validator for sierpinski image pattern, when width not dividable by 3, returns validation warning`() {
        val configuration =
            Configuration(
                width = 200,
                height = 200,
                amount = 10,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validationResult = ConfigurationValidator.validate(configuration)
        assertEquals(
            R.string.image_creator_option_invalid_not_dividable_by_3,
            validationResult.widthValidationMessage
        )
    }
    // endregion
}