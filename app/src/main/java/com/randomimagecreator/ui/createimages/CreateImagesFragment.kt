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
import com.randomimagecreator.helpers.parse
import com.randomimagecreator.ui.shared.MainViewModel
import com.randomimagecreator.ui.shared.State


/**
 * Shows the image creation options.
 */
class CreateImagesFragment : Fragment(R.layout.fragment_image_creation) {
    private lateinit var createButton: Button
    private lateinit var amountTextField: TextInputEditText
    private lateinit var widthTextField: TextInputEditText
    private lateinit var heightTextField: TextInputEditText
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createButton = view.findViewById(R.id.image_creator_button_create)

        amountTextField =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_amount).apply {
                doOnTextChanged { text, _, _, _ ->
                    viewModel.imageCreatorOptions.value?.amount = Int.parse(text)
                }
            }
        widthTextField =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_width).apply {
                doOnTextChanged { text, _, _, _ ->
                    viewModel.imageCreatorOptions.value?.width = Int.parse(text)
                }
            }
        heightTextField =
            view.findViewById<TextInputEditText>(R.id.image_creator_option_height).apply {
                doOnTextChanged { text, _, _, _ ->
                    viewModel.imageCreatorOptions.value?.height = Int.parse(text)
                }
            }

        view.findViewById<AutoCompleteTextView>(R.id.image_creator_option_pattern)
            .apply {
                setAdapter(
                    NoFilterArrayAdapter(
                        this@CreateImagesFragment.requireContext(),
                        R.layout.dropdown_item,
                        capitalizedValuesOf<ImagePattern>()
                    )
                )

                setText(viewModel.imageCreatorOptions.value!!.pattern.capitalized(), false)

                doOnTextChanged { text, _, _, _ ->
                    viewModel.imageCreatorOptions.value?.pattern =
                        ImagePattern.valueOf(text!!.toString().uppercase())
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

                setText(viewModel.imageCreatorOptions.value?.format.toString(), false)

                doOnTextChanged { text, _, _, _ ->
                    viewModel.imageCreatorOptions.value?.format =
                        ImageFileFormat.valueOf(text!!.toString())
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
            amountTextField.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val width = viewModel.imageCreatorOptions.value?.width
        if (width == null || width == 0) {
            widthTextField.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val height = viewModel.imageCreatorOptions.value?.height
        if (height == null || height == 0) {
            heightTextField.error = resources.getString(R.string.image_creator_option_invalid)
        }
    }
}

