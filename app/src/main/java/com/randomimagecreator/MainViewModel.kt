package com.randomimagecreator

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.common.extensions.query
import com.randomimagecreator.configuration.Configuration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * ViewModel used throughout the app.
 * */
class MainViewModel : ViewModel() {
    val configuration = Configuration()
    val screen: StateFlow<Screen> get() = _screen
    private val _screen = MutableStateFlow<Screen>(Screen.Configuration)
    val validationResult: Flow<Boolean> get() = _validationResult.filterNotNull()
    private val _validationResult = MutableStateFlow<Boolean?>(null)
    var createdImageUris = listOf<Uri>()
    var bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()

    fun onUserNavigatedBackToConfiguration() {
        _screen.value = Screen.Configuration
        _validationResult.value = null
    }

    suspend fun onUserSubmitsConfiguration() {
        val isValid = configuration.validator.isValid
        _validationResult.emit(isValid)
        if (isValid) {
            _screen.value = Screen.ChooseSaveDirectory
        }
    }

    fun onSaveDirectoryChosen(uri: Uri) {
        configuration.saveDirectory = uri
        _screen.value = Screen.ChooseSaveDirectory
    }

    fun createImages(context: Context) {
        val saveDirectory = configuration.saveDirectory ?: run {
            throw IllegalStateException("Image creation attempted without save directory ")
        }

        _screen.value = Screen.Loading

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
            _screen.value = Screen.Result(durationMillis)
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
