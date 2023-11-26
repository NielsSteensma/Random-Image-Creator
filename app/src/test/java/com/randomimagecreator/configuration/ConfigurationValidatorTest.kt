package com.randomimagecreator.configuration

import com.randomimagecreator.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ConfigurationValidatorForAllImagePatternsTest(imagePattern: ImagePattern) {
    private val defaultValidConfiguration = Configuration(
        12,
        600,
        600,
        pattern = imagePattern
    )

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "pattern {0}")
        fun data() = ImagePattern.values()
    }

    @Test
    fun `when valid, returns no validation warning`() {
        val validator = ConfigurationValidator(defaultValidConfiguration)
        assertNull(validator.isAmountValid())
    }

    @Test
    fun `when amount is invalid, returns value missing`() {
        val configuration = defaultValidConfiguration.copy(amount = 0)
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validator.isAmountValid()
        )
    }

    @Test
    fun `when width is invalid, returns validation warning`() {
        val configuration = defaultValidConfiguration.copy(width = 0)
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validator.widthValidationMessage()
        )
    }

    @Test
    fun `when height is invalid, returns validation warning`() {
        val configuration = defaultValidConfiguration.copy(height = 0)
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid,
            validator.heightValidationMessage()
        )
    }
}

class ConfigurationValidatorForSpecificImagePatternsTest {
    private val defaultConfiguration = Configuration(
        12,
        600,
        600
    )

    @Test
    fun `validator for Mandelbrot, when iterations is invalid, returns value missing`() {
        val configuration = defaultConfiguration.copy(
            iterations = 0,
            pattern = ImagePattern.MANDELBROT
        )
        val validator = ConfigurationValidator(configuration)
        assertEquals(R.string.image_creator_option_invalid, validator.iterationsValidationMessage())
    }

    @Test
    fun `validator for Sierpinski, when width is not dividable by 3, returns validation warning`() {
        val configuration =
            defaultConfiguration.copy(
                width = 133,
                height = 133,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid_not_dividable_by_3,
            validator.widthValidationMessage()
        )
    }

    @Test
    fun `validator for Sierpinski, when height is not dividable by 3, returns validation warning`() {
        val configuration =
            defaultConfiguration.copy(
                width = 133,
                height = 133,
                pattern = ImagePattern.SIERPINSKI_CARPET
            )
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid_not_dividable_by_3,
            validator.heightValidationMessage()
        )
    }

    @Test
    fun `validator for Sierpinski, when height and width are not equal, returns validation warning`() {
        val configuration =
            defaultConfiguration.copy(height = 300, pattern = ImagePattern.SIERPINSKI_CARPET)
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid_not_equal,
            validator.heightValidationMessage()
        )
    }

    @Test
    fun `validator for Sierpinski, when width and height are not equal, returns validation warning`() {
        val configuration =
            defaultConfiguration.copy(width = 300, pattern = ImagePattern.SIERPINSKI_CARPET)
        val validator = ConfigurationValidator(configuration)
        assertEquals(
            R.string.image_creator_option_invalid_not_equal,
            validator.heightValidationMessage()
        )
    }
}