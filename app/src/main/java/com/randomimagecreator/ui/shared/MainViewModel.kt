package com.randomimagecreator.ui.shared

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.analytics.Analytics
import com.randomimagecreator.common.ImageCreatorOptions
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.helpers.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * ViewModel used throughout the app.
 * */
class MainViewModel : ViewModel() {
    val imageCreatorOptions = MutableStateFlow(ImageCreatorOptions())
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Initial)
    var createdImageUris = listOf<Uri>()
    var bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()

    fun onUserSubmitsConfig() {
        if (imageCreatorOptions.value.isValid()) {
            _state.value = State.SubmitConfigValid
        } else {
            _state.value = State.SubmitConfigInvalid
        }
    }

    fun onSaveDirectoryChosen(uri: Uri) {
        imageCreatorOptions.value.saveDirectory = uri
        _state.value = State.SubmitSaveDirectory
    }

    fun createImages(context: Context) {
        val options = imageCreatorOptions.value
        if (!options.isValid()) {
            _state.value = State.SubmitConfigInvalid
            return
        }

        val saveDirectory = imageCreatorOptions.value.saveDirectory ?: run {
            throw IllegalStateException("Image creation attempted without save directory ")
        }

        _state.value = State.CreatingImages

        viewModelScope.launch(Dispatchers.IO) {
            Analytics.imageCreationEvent(options)
            val durationMillis = measureTimeMillis {
                val bitmaps = options.pattern.getImageCreator().createBitmaps(options)
                createdImageUris =
                    ImageSaver.saveBitmaps(
                        viewModelScope,
                        bitmaps,
                        context,
                        saveDirectory,
                        options.format,
                        bitmapSaveNotifier
                    )
            }
            _state.value = State.FinishedCreatingImages(durationMillis)
        }
    }

    fun getSaveDirectoryName(context: Context): String? {
        val saveDirectory = imageCreatorOptions.value.saveDirectory ?: run {
            return null
        }

        val saveDirectoryUri =
            DocumentFile.fromTreeUri(context, saveDirectory)?.uri ?: return null

        return context.contentResolver.query(saveDirectoryUri)?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (columnIndex == -1) return null
            return cursor.getStringOrNull(columnIndex)
        }
    }
}
