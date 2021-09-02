package com.randomimagecreator.ui.createdimages

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionBarContextView
import androidx.appcompat.widget.ActionMenuView
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.ImageCreatorOptions
import com.randomimagecreator.R
import com.randomimagecreator.ui.shared.BaseActivity

/**
 * Activity that shows a 2x2 grid list of images.
 */
class CreatedImagesActivity : BaseActivity() {
    private lateinit var adapter: CreatedImagesAdapter
    private lateinit var createdImageOptions: ImageCreatorOptions
    private lateinit var actionModeHandler: ActionModeHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createdImageOptions = intent.getParcelableExtra(INTENT_KEY_CREATED_IMAGE_OPTIONS)!!
        setContentView(R.layout.activity_created_images)
        setupRecyclerView()
        setupSupportActionBar(R.string.created_images_title)
        actionModeHandler = ActionModeHandler()
        setupTextFields()
        startActionMode(actionModeHandler)
        val actionBarContextView =
            this@CreatedImagesActivity.window.decorView.findViewById<View>(R.id.action_context_bar) as ActionBarContextView?
                ?: return
        val actionMenuView =
            actionBarContextView.children.firstOrNull { child -> child is ActionMenuView } ?: return
        for (child in (actionMenuView as ActionMenuView).children) {
            if (child is ActionMenuItemView) {
                child.setTextColor(resources.getColor(R.color.primary))
            }
        }
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
        findViewById<TextView>(R.id.textfield_created_images_location).setOnClickListener {
            val decorView =
                this@CreatedImagesActivity.window.decorView.findViewById<View>(R.id.action_mode_bar)
            val a = ""
        }
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

    private inner class ActionModeHandler : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            MenuInflater(this@CreatedImagesActivity).inflate(R.menu.menu_created_images, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val decorView =
                this@CreatedImagesActivity.window.decorView.findViewById<View>(R.id.action_mode_bar)
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = true

        override fun onDestroyActionMode(mode: ActionMode?) {
        }

    }
}
