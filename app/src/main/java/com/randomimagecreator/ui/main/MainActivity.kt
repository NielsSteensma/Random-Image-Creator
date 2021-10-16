package com.randomimagecreator.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.randomimagecreator.R
import com.randomimagecreator.ui.createdimages.CreatedImagesActivity
import com.randomimagecreator.ui.shared.BaseActivity
import com.randomimagecreator.analytics.AnalyticsManager
import com.randomimagecreator.helpers.parse

/**
 * Activity that shows to the user a form with the image creation options.
 */
class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var loadingIndicator: View
    private lateinit var createButton: Button
    private lateinit var amountTextField: TextInputEditText
    private lateinit var widthTextField: TextInputEditText
    private lateinit var heightTextField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AnalyticsManager.setup()
        setContentView(R.layout.activity_main)
        bindViews()
        bindViewModel()
    }

    /**
     * Binds the activity views to this class.
     */
    private fun bindViews() {
        loadingIndicator = findViewById(R.id.loading_indicator)
        createButton = findViewById(R.id.image_creator_button_create)
    }

    /**
     * Binds the activity to the view model.
     */
    private fun bindViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                MainViewModel.State.INVALID_FORM_FOUND ->
                    showValidationErrors()
                MainViewModel.State.STARTED_CREATING_IMAGES -> {
                    loadingIndicator.isVisible = true
                    createButton.isEnabled = false
                }
                MainViewModel.State.FINISHED_CREATING_IMAGES -> {
                    loadingIndicator.isVisible = false
                    createButton.isEnabled = true
                    navigateToCreatedImagesActivity()
                } else -> { }
            }
        }

        createButton.setOnClickListener {
            viewModel.onUserWantsToCreateImages(contentResolver)
        }

        amountTextField = findViewById<TextInputEditText>(R.id.image_creator_option_amount).apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.imageCreatorOptions.value?.amount = Int.parse(text)
            }
        }
        widthTextField = findViewById<TextInputEditText>(R.id.image_creator_option_width).apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.imageCreatorOptions.value?.width = Int.parse(text)
            }
        }
        heightTextField = findViewById<TextInputEditText>(R.id.image_creator_option_height).apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.imageCreatorOptions.value?.height = Int.parse(text)
            }
        }
    }

    /**
     * Shows validation errors for all invalid form fields.
     */
    private fun showValidationErrors() {
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

    /**
     * Starts the created images activity.
     */
    private fun navigateToCreatedImagesActivity() {
        // We pass URI's here because intent has 1mb limit.
        val intent = Intent(baseContext, CreatedImagesActivity::class.java).apply {
            putExtra(
                CreatedImagesActivity.INTENT_KEY_CREATED_IMAGE_URIS,
                viewModel.createdImageUris
            )
            putExtra(
                CreatedImagesActivity.INTENT_KEY_CREATED_IMAGE_OPTIONS,
                viewModel.imageCreatorOptions.value!!
            )
            putExtra(
                CreatedImagesActivity.INTENT_KEY_CREATED_IMAGES_DIRECTORY,
                viewModel.saveDirectory
            )
        }

        startActivity(intent)
    }
}
