package com.randomimagecreator

import android.app.Application
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.randomimagecreator.common.errors.SaveDirectoryMissingError
import com.randomimagecreator.common.extensions.contentResolver
import com.randomimagecreator.common.extensions.context
import com.randomimagecreator.common.extensions.query
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.validation.ConfigurationValidationResult
import com.randomimagecreator.configuration.validation.ConfigurationValidator
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
class MainViewModel(application: Application) : AndroidViewModel(application) {
    val configuration = Configuration()
    var navigationRequestBroadcaster: MutableSharedFlow<Screen> = MutableSharedFlow()
    val validationResult: Flow<ConfigurationValidationResult> get() = _validationResult.filterNotNull()
    val imageCreator = ImageCreator()
    lateinit var imageCreationResult: ImageCreationResult
        private set
    private val _validationResult = MutableStateFlow(ConfigurationValidationResult.valid())

    fun onUserWantsToGoBackToConfiguration() {
        _validationResult.value = ConfigurationValidationResult.valid()
        viewModelScope.launch {
            navigationRequestBroadcaster.emit(Screen.Configuration)
        }
    }

    suspend fun onUserSubmitsConfiguration() {
        val validationResult = ConfigurationValidator.validate(configuration)
        _validationResult.emit(validationResult)
        if (validationResult.isValid) {
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

    fun createImages() {
        val saveDirectoryUri = configuration.saveDirectory ?: throw SaveDirectoryMissingError()
        val context = getApplication<Application>()
        val saveDirectory =
            DocumentFile.fromTreeUri(context, saveDirectoryUri) ?: throw SaveDirectoryMissingError()
        viewModelScope.launch(Dispatchers.IO) {
            imageCreator.create(contentResolver, saveDirectory, configuration).fold(
                onSuccess = {
                    imageCreationResult = it
                    navigationRequestBroadcaster.emit(Screen.Result)
                },
                onFailure = {
                    try {
                        FirebaseCrashlytics.getInstance().recordException(it)
                    } catch (exception: IllegalStateException) {
                        Log.e(MainViewModel::class.toString(), "Crashlytics not initialized")
                    }
                    Log.e(MainViewModel::class.toString(), it.stackTraceToString())
                    navigationRequestBroadcaster.emit(Screen.Error)
                }
            )
        }
    }

    fun getSaveDirectoryName(saveDirectory: Uri): String? {
        val saveDirectoryUri = DocumentFile.fromTreeUri(context, saveDirectory)?.uri ?: return null
        return contentResolver.query(saveDirectoryUri)?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (columnIndex == -1) return null
            return cursor.getStringOrNull(columnIndex)
        }
    }
}
