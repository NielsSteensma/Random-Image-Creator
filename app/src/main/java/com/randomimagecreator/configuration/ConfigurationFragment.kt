package com.randomimagecreator.configuration

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.randomimagecreator.R
import com.randomimagecreator.common.extensions.toInt
import com.randomimagecreator.MainViewModel
import com.randomimagecreator.State
import kotlinx.coroutines.launch

/**
 * Shows image generation configuration.
 */
class ConfigurationFragment : Fragment(R.layout.fragment_configuration) {
    private lateinit var createButton: Button
    private lateinit var amountTextInput: TextInputEditText
    private lateinit var widthTextInput: TextInputEditText
    private lateinit var heightTextInput: TextInputEditText
    private lateinit var iterationsTextInput: TextInputEditText
    private lateinit var iterationsTextInputLayout: TextInputLayout
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createButton = view.findViewById(R.id.configuration_button_create)

        amountTextInput =
            view.findViewById<TextInputEditText>(R.id.configuration_amount).apply {
                isLongClickable = false
                doOnTextChanged { amount, _, _, _ ->
                    amount?.toInt()?.let {
                        viewModel.configuration.amount = it
                    }
                }
            }

        widthTextInput =
            view.findViewById<TextInputEditText>(R.id.configuration_width).apply {
                isLongClickable = false
                doOnTextChanged { width, _, _, _ ->
                    width?.toInt()?.let {
                        viewModel.configuration.width = it
                    }
                }
            }

        heightTextInput =
            view.findViewById<TextInputEditText>(R.id.configuration_height).apply {
                isLongClickable = false
                doOnTextChanged { height, _, _, _ ->
                    height?.toInt()?.let {
                        viewModel.configuration.height = it
                    }
                }
            }

        iterationsTextInputLayout = view.findViewById(R.id.configuration_iterations_layout)
        iterationsTextInput =
            view.findViewById<TextInputEditText>(R.id.configuration_iterations).apply {
                this.setText(viewModel.configuration.iterations.toString())
                doOnTextChanged { iterations, _, _, _ ->
                    iterations?.toInt()?.let {
                        viewModel.configuration.iterations = it
                    }
                }
            }

        view.findViewById<AutoCompleteTextView>(R.id.configuration_pattern)
            .apply {
                setAdapter(
                    NoFilterArrayAdapter(
                        this@ConfigurationFragment.requireContext(),
                        R.layout.dropdown_item,
                        ImagePattern.values()
                            .map { requireContext().getString(it.localizationResourceId) }
                    )
                )
            }

        view.findViewById<AutoCompleteTextView>(R.id.configuration_pattern).apply {
            setAdapter(
                NoFilterArrayAdapter(
                    this@ConfigurationFragment.requireContext(),
                    R.layout.dropdown_item,
                    ImagePattern.values()
                        .map { requireContext().getString(it.localizationResourceId) }
                )
            )

            setText(
                requireContext().getString(
                    viewModel.configuration.pattern.localizationResourceId,
                    false
                )
            )

            doOnTextChanged { charSequence, _, _, _ ->
                val imagePattern = when (charSequence.toString()) {
                    requireContext().getString(R.string.image_creator_solid) -> ImagePattern.SOLID
                    requireContext().getString(R.string.image_creator_pixelated) -> ImagePattern.PIXELATED
                    requireContext().getString(R.string.image_creator_mandelbrot) -> ImagePattern.MANDELBROT
                    requireContext().getString(R.string.image_creator_sierpinski) -> ImagePattern.SIERPINSKI_CARPET
                    else -> {
                        ImagePattern.SOLID
                    }
                }
                viewModel.configuration.pattern = imagePattern
                iterationsTextInputLayout.isVisible =
                    imagePattern == ImagePattern.MANDELBROT
            }

            doOnTextChanged { charSequence, _, _, _ ->
                val imagePattern = when (charSequence.toString()) {
                    requireContext().getString(R.string.image_creator_solid) -> ImagePattern.SOLID
                    requireContext().getString(R.string.image_creator_pixelated) -> ImagePattern.PIXELATED
                    requireContext().getString(R.string.image_creator_mandelbrot) -> ImagePattern.MANDELBROT
                    requireContext().getString(R.string.image_creator_sierpinski) -> ImagePattern.SIERPINSKI_CARPET
                    else -> {
                        ImagePattern.SOLID
                    }
                }
                viewModel.configuration.pattern = imagePattern
            }
        }

        view.findViewById<AutoCompleteTextView>(R.id.configuration_image_file_format)
            .apply {
                setAdapter(
                    NoFilterArrayAdapter(
                        this@ConfigurationFragment.requireContext(),
                        R.layout.dropdown_item,
                        ImageFileFormat.values()
                    )
                )

                setText(viewModel.configuration.format.toString(), false)

                doOnTextChanged { format, _, _, _ ->
                    viewModel.configuration.format =
                        ImageFileFormat.valueOf(format!!.toString())
                }
            }

        lifecycleScope.launch {
            viewModel.state.collect(::maybeShowValidationErrors)
        }

        createButton.setOnClickListener {
            viewModel.onUserSubmitsConfiguration()
        }
    }

    private fun maybeShowValidationErrors(state: State) {
        if (state != State.SubmittedConfigurationInvalid) {
            return
        }

        viewModel.configuration.validator.amountValidationMessage()?.let {
            amountTextInput.error = resources.getString(it)
        }
        viewModel.configuration.validator.widthValidationMessage()?.let {
            widthTextInput.error = resources.getString(it)
        }
        viewModel.configuration.validator.heightValidationMessage()?.let {
            heightTextInput.error = resources.getString(it)
        }
        viewModel.configuration.validator.iterationsValidationMessage()?.let {
            iterationsTextInput.error = resources.getString(it)
        }
    }
}

