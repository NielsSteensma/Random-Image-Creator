package com.randomimagecreator.ui.createimages

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
import com.randomimagecreator.common.ImageFileFormat
import com.randomimagecreator.common.ImagePattern
import com.randomimagecreator.helpers.toInt
import com.randomimagecreator.ui.shared.MainViewModel
import com.randomimagecreator.ui.shared.State
import kotlinx.coroutines.launch

/**
 * Shows the image creation options.
 */
class CreateImagesFragment : Fragment(R.layout.fragment_image_creation) {
    private lateinit var createButton: Button
    private lateinit var amountTextInput: TextInputEditText
    private lateinit var widthTextInput: TextInputEditText
    private lateinit var heightTextInput: TextInputEditText
    private lateinit var iterationsTextInput: TextInputEditText
    private lateinit var iterationsTextInputLayout: TextInputLayout
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createButton = view.findViewById(R.id.image_creator_button_create)

        amountTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_amount).apply {
                isLongClickable = false
                doOnTextChanged { amount, _, _, _ ->
                    amount?.toInt()?.let {
                        viewModel.imageCreatorOptions.value.amount = it
                    }
                }
            }

        widthTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_width).apply {
                isLongClickable = false
                doOnTextChanged { width, _, _, _ ->
                    width?.toInt()?.let {
                        viewModel.imageCreatorOptions.value.width = it
                    }
                }
            }

        heightTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_height).apply {
                isLongClickable = false
                doOnTextChanged { height, _, _, _ ->
                    height?.toInt()?.let {
                        viewModel.imageCreatorOptions.value.height = it
                    }
                }
            }

        iterationsTextInputLayout = view.findViewById(R.id.image_creator_option_iterations_layout)
        iterationsTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_iterations).apply {
                this.setText(viewModel.imageCreatorOptions.value.iterations.toString())
                doOnTextChanged { iterations, _, _, _ ->
                    iterations?.toInt()?.let {
                        viewModel.imageCreatorOptions.value.iterations = it
                    }
                }
            }

        view.findViewById<AutoCompleteTextView>(R.id.image_creator_option_pattern)
            .apply {
                setAdapter(
                    NoFilterArrayAdapter(
                        this@CreateImagesFragment.requireContext(),
                        R.layout.dropdown_item,
                        ImagePattern.values().map { requireContext().getString(it.localizationResourceId) }
                    )
                )
            }

        view.findViewById<AutoCompleteTextView>(R.id.image_creator_option_pattern).apply {
            setAdapter(
                NoFilterArrayAdapter(
                    this@CreateImagesFragment.requireContext(),
                    R.layout.dropdown_item,
                    ImagePattern.values().map { requireContext().getString(it.localizationResourceId) }
                )
            )

            setText(requireContext().getString(viewModel.imageCreatorOptions.value.pattern.localizationResourceId, false))

            doOnTextChanged { charSequence, _, _, _ ->
                val imagePattern = when(charSequence.toString()) {
                    requireContext().getString(R.string.image_creator_solid) -> ImagePattern.SOLID
                    requireContext().getString(R.string.image_creator_pixelated) -> ImagePattern.PIXELATED
                    requireContext().getString(R.string.image_creator_mandelbrot) -> ImagePattern.MANDELBROT
                    requireContext().getString(R.string.image_creator_sierpinski) -> ImagePattern.SIERPINSKI_CARPET
                    else -> { ImagePattern.SOLID }
                }
                viewModel.imageCreatorOptions.value.pattern = imagePattern
                iterationsTextInputLayout.isVisible =
                    imagePattern == ImagePattern.MANDELBROT
            }

            doOnTextChanged { charSequence, _, _, _ ->
                val imagePattern = when(charSequence.toString()) {
                    requireContext().getString(R.string.image_creator_solid) -> ImagePattern.SOLID
                    requireContext().getString(R.string.image_creator_pixelated) -> ImagePattern.PIXELATED
                    requireContext().getString(R.string.image_creator_mandelbrot) -> ImagePattern.MANDELBROT
                    requireContext().getString(R.string.image_creator_sierpinski) -> ImagePattern.SIERPINSKI_CARPET
                    else -> { ImagePattern.SOLID }
                }
                viewModel.imageCreatorOptions.value.pattern = imagePattern
            }
        }

        view.findViewById<AutoCompleteTextView>(R.id.image_creator_option_image_file_format)
            .apply {
                setAdapter(
                    NoFilterArrayAdapter(
                        this@CreateImagesFragment.requireContext(),
                        R.layout.dropdown_item,
                        ImageFileFormat.values()
                    )
                )

                setText(viewModel.imageCreatorOptions.value.format.toString(), false)

                doOnTextChanged { format, _, _, _ ->
                    viewModel.imageCreatorOptions.value.format =
                        ImageFileFormat.valueOf(format!!.toString())
                }
            }

        lifecycleScope.launch {
            viewModel.state
                .collect(::maybeShowValidationErrors)
        }

        createButton.setOnClickListener {
            viewModel.onUserSubmitsConfig()
        }
    }

    private fun maybeShowValidationErrors(state: State) {
        if (state != State.SubmitConfigInvalid) {
            return
        }

        val amount = viewModel.imageCreatorOptions.value.amount
        if (amount == 0) {
            amountTextInput.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val width = viewModel.imageCreatorOptions.value.width
        if (width == 0) {
            widthTextInput.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val height = viewModel.imageCreatorOptions.value.height
        if (height == 0) {
            heightTextInput.error = resources.getString(R.string.image_creator_option_invalid)
        }

        if (iterationsTextInputLayout.isVisible) {
            val iterations = viewModel.imageCreatorOptions.value.iterations
            if (iterations == 0) {
                iterationsTextInput.error =
                    resources.getString(R.string.image_creator_option_invalid)
            }
        }
    }
}

