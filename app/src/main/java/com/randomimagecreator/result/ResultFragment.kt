package com.randomimagecreator.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.MainViewModel
import com.randomimagecreator.R

/**
 * Amount of images to display on a single grid row.
 */
private const val GRID_SPAN_COUNT = 2

/**
 * Shows a 2x2 grid list of the generated images.
 */
internal class ResultFragment : Fragment(R.layout.fragment_result) {
    private lateinit var adapter: ResultAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.onCreateView(inflater, container, savedInstanceState)?.also {
        setupRecyclerView(it)
        setupTextFields(it)
    }

    private fun setupRecyclerView(rootView: View) {
        val recyclerview: RecyclerView = rootView.findViewById(R.id.recyclerview_created_images)

        recyclerview.layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        adapter = ResultAdapter(viewModel.imageCreationResult.uris)
        recyclerview.adapter = adapter
        val itemDecorator = GridLayoutSpacingItemDecorator(
            GRID_SPAN_COUNT,
            resources.getDimension(R.dimen.result_grid_spacing)
        )
        recyclerview.addItemDecoration(itemDecorator)
    }

    private fun setupTextFields(rootView: View) {
        rootView.findViewById<TextView>(R.id.created_images_amount).apply {
            text = viewModel.imageCreationResult.uris.size.toString()
        }

        rootView.findViewById<TextView>(R.id.created_images_pattern).apply {
            text = resources.getString(viewModel.configuration.pattern.localizationResourceId)
        }

        rootView.findViewById<TextView>(R.id.created_images_directory).apply {
            text = viewModel.getSaveDirectoryName(requireContext()) ?: ""
        }

        rootView.findViewById<TextView>(R.id.created_images_duration).apply {
            text = DurationFormatter
                .seconds(viewModel.imageCreationResult.durationInMilliseconds)
                .toString()
        }
    }
}
