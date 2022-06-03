package com.randomimagecreator.ui.shared

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.ImageCreatorOptions
import com.randomimagecreator.analytics.AnalyticsManager
import com.randomimagecreator.creators.SolidColorCreator
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.helpers.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel used throughout the app.
 * */
class MainViewModel : ViewModel() {
    val imageCreatorOptions = MutableLiveData(ImageCreatorOptions())
    val state = MutableLiveData(State.INITIAL)
    var createdImageUris = listOf<Uri>()
    private var saveDirectory: Uri? = null

    fun onUserSubmitsConfig() {
        if (imageCreatorOptions.value?.isValid() == true) {
            state.postValue(State.SUBMIT_CONFIG_VALID)
        } else {
            state.postValue(State.SUBMIT_CONFIG_INVALID)
        }
    }

    fun onSaveDirectoryChosen(uri: Uri) {
        saveDirectory = uri
        state.postValue(State.SUBMIT_SAVE_DIRECTORY)
    }

    fun createImages(context: Context) {
        val options = imageCreatorOptions.value
        if (options == null || !options.isValid()) {
            state.postValue(State.SUBMIT_CONFIG_INVALID)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (saveDirectory == null) return@launch

            AnalyticsManager.logImageCreationEvent(options)
            val bitmaps = SolidColorCreator().createBitmaps(options)
            createdImageUris = ImageSaver.saveBitmaps(bitmaps, context, saveDirectory!!, options.format)
            state.postValue(State.FINISHED_CREATING_IMAGES)
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
