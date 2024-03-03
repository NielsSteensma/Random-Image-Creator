package com.randomimagecreator.choosesavedirectory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.randomimagecreator.MainViewModel
import com.randomimagecreator.R
import kotlinx.coroutines.launch

/**
 * Shows an explanation to the user that he needs to select the directory to save his files.
 */
class ChooseSaveDirectoryFragment : Fragment(R.layout.fragment_choose_save_directory) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: ChooseSaveDirectoryViewModel by viewModels(
        ownerProducer = { this }
    )
    private val launcher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        uri?.let {
            viewModel.persistSaveDirectory(it)
            mainViewModel.onSaveDirectoryChosen(it)
            mainViewModel.createImages()
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

        val reuseButton = rootView.findViewById<Button>(R.id.choose_save_directory_reuse_button)
        lifecycleScope.launch {
            val saveDirectory = viewModel.getLastPersistedSaveDirectory()
            val saveDirectoryName =
                saveDirectory?.let { mainViewModel.getSaveDirectoryName(saveDirectory) }
            if (saveDirectory != null && saveDirectoryName != null) {
                reuseButton.isVisible = true
                reuseButton.text = requireContext().getString(
                    R.string.choose_save_directory_reuse,
                    saveDirectoryName
                )
            } else {
                reuseButton.isVisible = false
            }
        }
        reuseButton.setOnClickListener {
            lifecycleScope.launch {
                val lastPersistedSaveDirectory = viewModel.getLastPersistedSaveDirectory()
                assert(lastPersistedSaveDirectory != null) { "null but reuse enabled" }
                lastPersistedSaveDirectory?.let {
                    mainViewModel.onSaveDirectoryChosen(lastPersistedSaveDirectory)
                    mainViewModel.createImages()
                }
            }
        }
    }
}