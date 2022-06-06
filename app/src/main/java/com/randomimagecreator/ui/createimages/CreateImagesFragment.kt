package com.randomimagecreator.ui.createimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.randomimagecreator.R
import com.randomimagecreator.common.ImageFileFormat
import com.randomimagecreator.common.ImagePattern
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        super.onCreateView(inflater, container, savedInstanceState)?.also {
            createButton = it.findViewById(R.id.image_creator_button_create)

            amountTextField =
                it.findViewById<TextInputEditText>(R.id.image_creator_option_amount).apply {
                    doOnTextChanged { text, _, _, _ ->
                        viewModel.imageCreatorOptions.value?.amount = Int.parse(text)
                    }
                }
            widthTextField =
                it.findViewById<TextInputEditText>(R.id.image_creator_option_width).apply {
                    doOnTextChanged { text, _, _, _ ->
                        viewModel.imageCreatorOptions.value?.width = Int.parse(text)
                    }
                }
            heightTextField =
                it.findViewById<TextInputEditText>(R.id.image_creator_option_height).apply {
                    doOnTextChanged { text, _, _, _ ->
                        viewModel.imageCreatorOptions.value?.height = Int.parse(text)
                    }
                }

            it.findViewById<AutoCompleteTextView>(R.id.image_creator_option_pattern)
                .apply {
                    this.setAdapter(
                        ArrayAdapter(
                            this@CreateImagesFragment.requireContext(),
                            R.layout.dropdown_item,
                            capitalizedValuesOf<ImagePattern>()
                        )
                    )

                    this.setText(capitalizedValuesOf<ImagePattern>()[0], false)
                    doOnTextChanged { text, _, _, _ ->
                        viewModel.imageCreatorOptions.value?.pattern =
                            ImagePattern.valueOf(text!!.toString().uppercase())
                    }
                }

            it.findViewById<AutoCompleteTextView>(R.id.image_creator_option_image_file_format)
                .apply {
                    this.setAdapter(
                        ArrayAdapter(
                            this@CreateImagesFragment.requireContext(),
                            R.layout.dropdown_item,
                            ImageFileFormat.values()
                        )
                    )

                    this.setText(ImageFileFormat.JPEG.name, false)
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
