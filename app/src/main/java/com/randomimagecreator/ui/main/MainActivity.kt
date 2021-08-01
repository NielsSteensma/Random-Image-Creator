package com.randomimagecreator.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.randomimagecreator.R
import com.randomimagecreator.ui.createdimages.CreatedImagesActivity

/**
 * Activity that shows to the user the form with the image creation options.
 */
class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    private lateinit var amountTextField: TextInputEditText
    private lateinit var widthTextField: TextInputEditText
    private lateinit var heightTextField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindToViewModel()
    }

    /**
     * Binds the activity to the viewModel.
     */
    private fun bindToViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                MainViewModel.State.INVALID_FORM_FOUND ->
                    showValidationErrors()
                MainViewModel.State.FINISHED_CREATING_IMAGES ->
                    navigateToCreatedImagesActivity()
                else -> {
                }
            }
        }

        findViewById<Button>(R.id.image_creator_button_create).setOnClickListener {
            viewModel.onUserWantsToCreateImages(contentResolver)
        }

        amountTextField = findViewById<TextInputEditText>(R.id.image_creator_option_amount).apply {
            doOnTextChanged { text, _, _, _ -> onTextChangedHandler(viewModel.amount, text) }
        }
        widthTextField = findViewById<TextInputEditText>(R.id.image_creator_option_width).apply {
            doOnTextChanged { text, _, _, _ -> onTextChangedHandler(viewModel.width, text) }
        }
        heightTextField = findViewById<TextInputEditText>(R.id.image_creator_option_height).apply {
            doOnTextChanged { text, _, _, _ -> onTextChangedHandler(viewModel.height, text) }
        }
    }

    /**
     * Convenient method that updates the given [MutableLiveData] of type [Int] with the given
     * [value] if the value is not null, otherwise sets the value as 0.
     */
    private fun onTextChangedHandler(liveData: MutableLiveData<Int>, value: CharSequence?) {
        liveData.value = if (value.isNullOrBlank()) {
            0
        } else {
            Integer.parseInt(value.toString())
        }
    }

    /**
     * Shows validation errors for all invalid form fields.
     */
    private fun showValidationErrors() {
        val amount = viewModel.amount.value?.toString()
        if (amount == null || amount == "0") {
            amountTextField.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val width = viewModel.width.value?.toString()
        if (width == null || width == "0") {
            widthTextField.error = resources.getString(R.string.image_creator_option_invalid)
        }

        val height = viewModel.height.value?.toString()
        if (height == null || height == "0") {
            heightTextField.error = resources.getString(R.string.image_creator_option_invalid)
        }
    }

    private fun navigateToCreatedImagesActivity() {
        // We pass URI's here because intent has 1mb limit
        val intent = Intent(baseContext, CreatedImagesActivity::class.java).apply {
            putExtra(
                CreatedImagesActivity.INTENT_KEY_CREATED_IMAGE_URIS,
                viewModel.createdImageUris
            )
            putExtra(
                CreatedImagesActivity.INTENT_KEY_CREATED_IMAGE_OPTIONS,
                viewModel.imageCreatorOptions
            )
        }

        startActivity(intent)
    }
}