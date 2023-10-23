package com.randomimagecreator

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.common.extensions.query
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
    val configuration = Configuration()
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Initial)
    var createdImageUris = listOf<Uri>()
    var bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()

    fun onUserSubmitsConfiguration() {
        if (configuration.isValid()) {
            _state.value = State.SubmittedConfigurationValid
        } else {
            _state.value = State.SubmittedConfigurationInvalid
        }
    }

    fun onSaveDirectoryChosen(uri: Uri) {
        configuration.saveDirectory = uri
        _state.value = State.SubmitSaveDirectory
    }

    fun createImages(context: Context) {
        if (!configuration.isValid()) {
            _state.value = State.SubmittedConfigurationInvalid
            return
        }

        val saveDirectory = configuration.saveDirectory ?: run {
            throw IllegalStateException("Image creation attempted without save directory ")
        }

        _state.value = State.CreatingImages

        viewModelScope.launch(Dispatchers.IO) {
            Analytics.imageCreationEvent(configuration)
            val durationMillis = measureTimeMillis {
                val bitmaps =
                    configuration.pattern.getImageGenerator().createBitmaps(configuration)
                createdImageUris =
                    ImageSaver.saveBitmaps(
                        viewModelScope,
                        bitmaps,
                        context,
                        saveDirectory,
                        configuration.format,
                        bitmapSaveNotifier
                    )
            }
            _state.value = State.FinishedCreatingImages(durationMillis)
        }
    }

    fun getSaveDirectoryName(context: Context): String? {
        val saveDirectory = configuration.saveDirectory ?: run {
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
