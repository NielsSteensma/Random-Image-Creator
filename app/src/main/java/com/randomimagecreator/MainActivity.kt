package com.randomimagecreator

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.randomimagecreator.algorithms.SolidColorGenerator
import com.randomimagecreator.helpers.ImageSaver

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tempGenerateButton = findViewById<Button>(R.id.temp_button)
        tempGenerateButton.setOnClickListener(GenerateImageButtonOnClickListener())
    }

    inner class GenerateImageButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val imageView = findViewById<ImageView>(R.id.temp_imageview)
            val bitmap = SolidColorGenerator().generateBitmap()
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 200, 200, false))
            imageView.invalidate()

            // Save to storage
            ImageSaver.saveBitmap(bitmap, contentResolver)
        }
    }
}