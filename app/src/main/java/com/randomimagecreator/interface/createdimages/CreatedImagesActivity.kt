package com.randomimagecreator.`interface`.createdimages

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.ImageCreatorOptions
import com.randomimagecreator.R
import com.randomimagecreator.`interface`.shared.BaseActivity

/**
 * Activity that shows a 2x2 grid list of images.
 */
class CreatedImagesActivity : BaseActivity() {
    private lateinit var adapter: CreatedImagesAdapter
    private lateinit var createdImageOptions: ImageCreatorOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createdImageOptions = intent.getParcelableExtra(INTENT_KEY_CREATED_IMAGE_OPTIONS)!!
        setContentView(R.layout.activity_created_images)
        setupRecyclerView()
        setupTextFields()
    }

    /**
     * Sets up the RecyclerView to show the 2x2 grid of images.
     */
    private fun setupRecyclerView() {
        val recyclerview: RecyclerView = findViewById(R.id.recyclerview_created_images)
        val createdImages: ArrayList<Uri> = intent.getParcelableArrayListExtra(
            INTENT_KEY_CREATED_IMAGE_URIS
        )!!

        recyclerview.layoutManager = GridLayoutManager(applicationContext, GRID_SPAN_COUNT)
        adapter = CreatedImagesAdapter(createdImages)
        recyclerview.adapter = adapter
    }

    private fun setupTextFields() {
        findViewById<TextView>(R.id.textfield_created_images_amount).apply {
            text = if (createdImageOptions.amount == 1) {
                resources.getString(R.string.created_images_amount_single)
            } else {
                resources.getString(
                    R.string.created_images_amount_multiple,
                    createdImageOptions.amount
                )
            }
        }
        findViewById<TextView>(R.id.textfield_created_images_location).text = resources.getString(
            R.string.created_images_location,
            createdImageOptions.storageDirectory
        )
    }

    companion object {
        /**
         * Key for passing the list of created image uris to this activity.
         */
        const val INTENT_KEY_CREATED_IMAGE_URIS = "createdImageUris"

        /**
         * Key for passing the list of image creation options to this activity.
         */
        const val INTENT_KEY_CREATED_IMAGE_OPTIONS = "createdImageOptions"

        /**
         * Amount of images to display on a single grid row.
         */
        private const val GRID_SPAN_COUNT = 2
    }
}
