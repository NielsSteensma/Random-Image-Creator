package com.randomimagecreator.ui.choosesavedirectory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.randomimagecreator.R
import com.randomimagecreator.ui.shared.MainViewModel

/**
 * Shows an explanation to the user that he needs to select the directory to save his files.
 */
class ChooseSaveDirectoryFragment : Fragment(R.layout.fragment_choose_save_directory) {
    private val viewModel: MainViewModel by activityViewModels()
    private val launcher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        uri?.let {
            viewModel.onSaveDirectoryChosen(it)
            viewModel.createImages(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.onCreateView(inflater, container, savedInstanceState).also {
        it?.findViewById<Button>(R.id.choose_save_directory_button)?.setOnClickListener {
            launcher.launch(null)
        }
    }
}