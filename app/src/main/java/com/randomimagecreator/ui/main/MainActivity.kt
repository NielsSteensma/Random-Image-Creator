package com.randomimagecreator.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.randomimagecreator.R
import com.randomimagecreator.creators.SolidColorCreator
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.ui.createdimages.CreatedImagesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tempGenerateButton = findViewById<Button>(R.id.temp_button)
        tempGenerateButton.setOnClickListener(GenerateImageButtonOnClickListener())
    }

    inner class GenerateImageButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val bitmaps = SolidColorCreator().createBitmaps()

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