package com.randomimagecreator.ui.creatingimages

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.randomimagecreator.R
import com.randomimagecreator.ui.shared.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Provides feedback to the user about the image creation process.
 */
class CreatingImagesFragment : Fragment(R.layout.fragment_creating_images) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var numberOfSavedImagesTextField: TextView
    private var numberOfSavedImages = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberOfSavedImagesTextField = view.findViewById(R.id.creating_images_saved_amount)

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.bitmapSaveNotifier.collect {
                numberOfSavedImages++
                updateNumberOfSavedImages()
            }
        }
    }

    private fun updateNumberOfSavedImages() {
        numberOfSavedImagesTextField.text = getString(
            R.string.creating_images_saved_amount,
            numberOfSavedImages,
            mainViewModel.imageCreatorOptions.value.amount
        )
    }
}
