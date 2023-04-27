package com.randomimagecreator.ui.createdimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.R
import com.randomimagecreator.ui.shared.MainViewModel
import com.randomimagecreator.ui.shared.State
import java.util.concurrent.TimeUnit

/**
 * Amount of images to display on a single grid row.
 */
private const val GRID_SPAN_COUNT = 2

/**
 * Shows a 2x2 grid list of images.
 */
internal class CreatedImagesFragment : Fragment(R.layout.fragment_created_images) {
    private lateinit var adapter: CreatedImagesAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.onCreateView(inflater, container, savedInstanceState)?.also {
        setupRecyclerView(it)
        setupTextFields(it)
    }

    /**
     * Sets up the RecyclerView to show the 2x2 grid of images.
     */
    private fun setupRecyclerView(rootView: View) {
        val recyclerview: RecyclerView = rootView.findViewById(R.id.recyclerview_created_images)

        recyclerview.layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        adapter = CreatedImagesAdapter(viewModel.createdImageUris)
        recyclerview.adapter = adapter
    }

    private fun setupTextFields(rootView: View) {
        rootView.findViewById<TextView>(R.id.created_images_amount).apply {
            text = viewModel.createdImageUris.size.toString()
        }

        rootView.findViewById<TextView>(R.id.created_images_pattern).apply {
            text = viewModel.imageCreatorOptions.value!!.pattern.toString()
        }

        rootView.findViewById<TextView>(R.id.created_images_directory).apply {
            text = viewModel.getSaveDirectoryName(requireContext()) ?: ""
        }

        rootView.findViewById<TextView>(R.id.created_images_duration).apply {
            val state = viewModel.state.value
            if (state != null && state is State.FinishedCreatingImages) {
                text = TimeUnit.MILLISECONDS.toSeconds(state.duration).toString()
            }
        }
    }
}
