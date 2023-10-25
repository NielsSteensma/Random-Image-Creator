package com.randomimagecreator.loading

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.randomimagecreator.R
import com.randomimagecreator.MainViewModel
import kotlinx.coroutines.launch

/**
 * Provides feedback to the user about the image generation.
 */
class LoadingFragment : Fragment(R.layout.fragment_loading) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var numberOfSavedImagesTextField: TextView
    private var numberOfSavedImages = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberOfSavedImagesTextField = view.findViewById(R.id.loading_saved_amount)

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.bitmapSaveNotifier.collect {
                numberOfSavedImages++
                updateNumberOfSavedImages()
            }
        }
    }

    private fun updateNumberOfSavedImages() {
        numberOfSavedImagesTextField.text = getString(
            R.string.loading_saved_amount,
            numberOfSavedImages,
            mainViewModel.configuration.amount
        )
    }
}
