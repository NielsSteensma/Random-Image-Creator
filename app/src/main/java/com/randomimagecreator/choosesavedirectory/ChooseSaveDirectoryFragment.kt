package com.randomimagecreator.choosesavedirectory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.randomimagecreator.MainViewModel
import com.randomimagecreator.R

/**
 * Shows an explanation to the user that he needs to select the directory to save his files.
 */
class ChooseSaveDirectoryFragment : Fragment(R.layout.fragment_choose_save_directory) {
    private val viewModel: MainViewModel by activityViewModels()
    private val launcher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        uri?.let {
            viewModel.onSaveDirectoryChosen(requireContext().contentResolver, it, true)
            viewModel.createImages(requireContext())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = super.onCreateView(inflater, container, savedInstanceState)?.also { rootView ->
        rootView.findViewById<Button>(R.id.choose_save_directory_button)?.setOnClickListener {
            launcher.launch(null)
        }
        val persistedUri =
            requireContext().contentResolver.persistedUriPermissions.first { it.isWritePermission }
        rootView.findViewById<Button>(R.id.choose_save_directory_reuse_button)?.also { button ->
            val reuseDirectoryName =
                viewModel.getSaveDirectoryName(requireContext(), persistedUri.uri)
            button.text =
                resources.getString(R.string.choose_save_directory_reuse_button, reuseDirectoryName)
            button.setOnClickListener {
                val uri = DocumentFile.fromTreeUri(requireContext(), persistedUri.uri)?.uri
                viewModel.onSaveDirectoryChosen(requireContext().contentResolver, uri!!, false)
                viewModel.createImages(requireContext())
            }
        }

    }
}