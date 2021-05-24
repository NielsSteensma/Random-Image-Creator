package com.randomimagecreator.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.randomimagecreator.ImageCreatorOptions
import com.randomimagecreator.R
import com.randomimagecreator.creators.SolidColorCreator
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.ui.createdimages.CreatedImagesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var amountTextField: TextInputEditText
    private lateinit var widthTextField: TextInputEditText
    private lateinit var heightTextField: TextInputEditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val createButton = findViewById<Button>(R.id.image_creator_button_create)
        createButton.setOnClickListener(GenerateImageButtonOnClickListener())

        // Bind fields
        amountTextField = findViewById(R.id.image_creator_option_amount)
        widthTextField = findViewById(R.id.image_creator_option_width)
        heightTextField = findViewById(R.id.image_creator_option_height)
    }

    /**
     * Validates if the creation can be performed. This might not be the case if options are missing
     * for example.
     *
     * For all found invalid fields an error will be shown to the user.
     */
    private fun validateCreation(): Boolean {
        var isValid = true
        if (amountTextField.text.toString().isBlank()) {
            amountTextField.error = resources.getString(R.string.image_creator_option_invalid)
            isValid = false
        }
        if (widthTextField.text.toString().isBlank()) {
            widthTextField.error = resources.getString(R.string.image_creator_option_invalid)
            isValid = false
        }
        if (heightTextField.text.toString().isBlank()) {
            heightTextField.error = resources.getString(R.string.image_creator_option_invalid)
            isValid = false
        }
        return isValid
    }

    private fun getImageCreatorOptions() =
        ImageCreatorOptions(
            amount = Integer.parseInt(amountTextField.text.toString()),
            width = Integer.parseInt(widthTextField.text.toString()),
            height = Integer.parseInt(heightTextField.text.toString())
        )

    inner class GenerateImageButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val isValid = validateCreation()
            if (!isValid) return


            val bitmaps = SolidColorCreator().createBitmaps(getImageCreatorOptions())

            // Save to storage
            val createdImageUris = ImageSaver.saveBitmaps(bitmaps, contentResolver)

            // Start activity to show generated bitmaps.
            // We pass URI's here because intent has 1mb limit
            val intent = Intent(baseContext, CreatedImagesActivity::class.java).apply {
                putExtra(CreatedImagesActivity.INTENT_KEY_CREATED_IMAGE_URIS, createdImageUris)
            }

            startActivity(intent)
        }
    }
}