package com.randomimagecreator.ui.shared

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.analytics.Analytics
import com.randomimagecreator.common.ImageCreatorOptions
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.helpers.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * ViewModel used throughout the app.
 * */
class MainViewModel : ViewModel() {
    val imageCreatorOptions = MutableLiveData(ImageCreatorOptions())
    val state = MutableLiveData<State>(State.Initial)
    var createdImageUris = listOf<Uri>()
    var bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()
    private var saveDirectory: Uri? = null

    fun onUserSubmitsConfig() {
        if (imageCreatorOptions.value?.isValid() == true) {
            state.postValue(State.SubmitConfigValid)
        } else {
            state.postValue(State.SubmitConfigInvalid)
        }
    }

    fun onSaveDirectoryChosen(uri: Uri) {
        saveDirectory = uri
        state.postValue(State.SubmitSaveDirectory)
    }

    fun createImages(context: Context) {
        val options = imageCreatorOptions.value
        if (options == null || !options.isValid()) {
            state.postValue(State.SubmitConfigValid)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (saveDirectory == null) return@launch

            Analytics.imageCreationEvent(options)
            val durationMillis = measureTimeMillis {
                val bitmaps = options.pattern.imageCreator.createBitmaps(options)
                createdImageUris =
                    ImageSaver.saveBitmaps(
                        viewModelScope,
                        bitmaps,
                        context,
                        saveDirectory!!,
                        options.format,
                        bitmapSaveNotifier
                    )
            }
            state.postValue(State.FinishedCreatingImages(durationMillis))
        }
    }

    fun getSaveDirectoryName(context: Context): String? {
        if (saveDirectory == null) return null

        val saveDirectoryUri =
            DocumentFile.fromTreeUri(context, saveDirectory!!)?.uri ?: return null

        return context.contentResolver.query(saveDirectoryUri)?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (columnIndex == -1) return null
            return cursor.getStringOrNull(columnIndex)
        }
    }
}
