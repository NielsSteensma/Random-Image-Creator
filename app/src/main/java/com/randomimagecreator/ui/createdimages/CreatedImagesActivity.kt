package com.randomimagecreator.ui.createdimages

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.R


/**
 * Activity that shows a 2x2 grid list of images.
 */
class CreatedImagesActivity : AppCompatActivity() {
    private lateinit var adapter: CreatedImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_created_images)
        setupRecyclerView()
    }

    /**
     * Sets up the recyclerview to show the 2x2 grid of images.
     */
    private fun setupRecyclerView() {
        val recyclerview: RecyclerView = findViewById(R.id.recyclerview_created_images)
        val createdImages: ArrayList<Uri> = intent.getParcelableArrayListExtra(
            INTENT_KEY_CREATED_IMAGE_URIS
        )

        recyclerview.layoutManager = GridLayoutManager(applicationContext, GRID_SPAN_COUNT)
        adapter = CreatedImagesAdapter(createdImages)
        recyclerview.adapter = adapter
    }

    companion object {
        /**
         * Key for passing the list of created image uris to this activity.
         */
        const val INTENT_KEY_CREATED_IMAGE_URIS = "createdImageUris"

        /**
         * Amount of images to display on a single grid row.
         */
        private const val GRID_SPAN_COUNT = 2
    }
}