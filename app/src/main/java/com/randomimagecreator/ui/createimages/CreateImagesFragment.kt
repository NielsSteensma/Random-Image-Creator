package com.randomimagecreator.ui.createimages

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.randomimagecreator.R
import com.randomimagecreator.common.ImageFileFormat
import com.randomimagecreator.common.ImagePattern
import com.randomimagecreator.helpers.capitalized
import com.randomimagecreator.helpers.capitalizedValuesOf
import com.randomimagecreator.helpers.toInt
import com.randomimagecreator.ui.shared.MainViewModel
import com.randomimagecreator.ui.shared.State

/**
 * Shows the image creation options.
 */
class CreateImagesFragment : Fragment(R.layout.fragment_image_creation) {
    private lateinit var createButton: Button
    private lateinit var amountTextInput: TextInputEditText
    private lateinit var widthTextInput: TextInputEditText
    private lateinit var heightTextInput: TextInputEditText
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createButton = view.findViewById(R.id.image_creator_button_create)

        amountTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_amount).apply {
                isLongClickable = false
                doOnTextChanged { amount, _, _, _ ->
                    amount?.toInt()?.let {
                        viewModel.imageCreatorOptions.value?.amount = it
                    }
                }
            }

        widthTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_width).apply {
                isLongClickable = false
                doOnTextChanged { width, _, _, _ ->
                    width?.toInt()?.let {
                        viewModel.imageCreatorOptions.value?.width = it
                    }
                }
            }

        heightTextInput =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_height).apply {
                isLongClickable = false
                doOnTextChanged { height, _, _, _ ->
                    height?.toInt()?.let {
                        viewModel.imageCreatorOptions.value?.height = it
                    }
                }
            }

        view.findViewById<AutoCompleteTextView>(R.id.image_creator_option_pattern).apply {
            setAdapter(
                NoFilterArrayAdapter(
                    this@CreateImagesFragment.requireContext(),
                    R.layout.dropdown_item,
                    capitalizedValuesOf<ImagePattern>()
                )
            )

            setText(viewModel.imageCreatorOptions.value!!.pattern.capitalized(), false)

            doOnTextChanged { pattern, _, _, _ ->
                viewModel.imageCreatorOptions.value?.pattern =
                    ImagePattern.valueOf(pattern!!.toString().uppercase())
            }
        }

        view.findViewById<AutoCompleteTextView>(R.id.image_creator_option_image_file_format).apply {
            setAdapter(
                NoFilterArrayAdapter(
                    this@CreateImagesFragment.requireContext(),
                    R.layout.dropdown_item,
                    ImageFileFormat.values()
                )
            )

            setText(viewModel.imageCreatorOptions.value?.format.toString(), false)

            doOnTextChanged { format, _, _, _ ->
                viewModel.imageCreatorOptions.value?.format =
                    ImageFileFormat.valueOf(format!!.toString())
            }
        }

        viewModel.state.observe(viewLifecycleOwner, ::maybeShowValidationErrors)

        createButton.setOnClickListener {
            viewModel.onUserSubmitsConfig()
        }
    }

    private fun maybeShowValidationErrors(state: State) {
        if (state != State.SUBMIT_CONFIG_INVALID) {
            return
        }

        val amount = viewModel.imageCreatorOptions.value?.amount
        if (amount == null || amount == 0) {
            amountTextInput.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val width = viewModel.imageCreatorOptions.value?.width
        if (width == null || width == 0) {
            widthTextInput.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val height = viewModel.imageCreatorOptions.value?.height
        if (height == null || height == 0) {
            heightTextInput.error = resources.getString(R.string.image_creator_option_invalid)
        }
    }
}

