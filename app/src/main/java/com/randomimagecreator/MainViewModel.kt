package com.randomimagecreator

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.common.errors.SaveDirectoryMissingError
import com.randomimagecreator.common.extensions.query
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern
import com.randomimagecreator.result.ImageCreationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


/**
 * ViewModel used throughout the app.
 * */
class MainViewModel : ViewModel() {
    val configuration = Configuration()
    var navigationRequestBroadcaster: MutableSharedFlow<Screen> = MutableSharedFlow()
    val validationResult: Flow<Boolean> get() = _validationResult.filterNotNull()
    val imageCreator = ImageCreator()
    private val _validationResult = MutableStateFlow<Boolean?>(null)
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
        val saveDirectoryUri = configuration.saveDirectory ?: throw SaveDirectoryMissingError()
        val saveDirectory =
            DocumentFile.fromTreeUri(context, saveDirectoryUri) ?: throw SaveDirectoryMissingError()
        val config = configuration.copy(width = -1)
        viewModelScope.launch(Dispatchers.IO) {
            imageCreator.create(context.contentResolver, saveDirectory, config).fold(
                onSuccess = {
                    imageCreationResult = it
                    navigationRequestBroadcaster.emit(Screen.Result)
                },
                onFailure = {
                    navigationRequestBroadcaster.emit(Screen.Error)
                }
            )
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
