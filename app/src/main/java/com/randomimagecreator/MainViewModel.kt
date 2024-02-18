package com.randomimagecreator

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.extensions.query
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.result.ImageCreationResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

/**
 * ViewModel used throughout the app.
 * */
class MainViewModel : ViewModel() {
    val configuration = Configuration()
    var navigationRequestBroadcaster: MutableSharedFlow<Screen> = MutableSharedFlow()
    val validationResult: Flow<Boolean> get() = _validationResult.filterNotNull()
    private val _validationResult = MutableStateFlow<Boolean?>(null)
    var bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()
    lateinit var imageCreationResult: ImageCreationResult
        private set

    fun onUserWantsToGoBackToConfiguration() {
        _validationResult.value = null
        viewModelScope.launch {
            navigationRequestBroadcaster.emit(Screen.Configuration)
        }
    }

    suspend fun onUserSubmitsConfiguration() {
        val isValid = configuration.validator.isValid
        _validationResult.emit(isValid)
        if (isValid) {
            viewModelScope.launch {
                navigationRequestBroadcaster.emit(Screen.ChooseSaveDirectory)
            }
        }
    }

    fun onSaveDirectoryChosen(uri: Uri) {
        configuration.saveDirectory = uri
        viewModelScope.launch {
            navigationRequestBroadcaster.emit(Screen.Loading)
        }
    }

    fun createImages(context: Context) {
        viewModelScope.launch {
            try {
                val saveDirectory = DocumentFile.fromTreeUri(context, configuration.saveDirectory!!)!!
                imageCreationResult =
                    ImageCreator().create(context.contentResolver, saveDirectory, configuration)
                navigationRequestBroadcaster.emit(Screen.Result)
            } catch (exception: Exception) {
                Log.e(TAG, "", exception)
            }
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
