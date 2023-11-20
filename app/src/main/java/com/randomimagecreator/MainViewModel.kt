package com.randomimagecreator

import android.app.Application
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.mutableStateOf
import androidx.core.database.getStringOrNull
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.common.extensions.query
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * ViewModel used throughout the app.
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    var configuration = Configuration()
    var result: Result? = null
        private set
    val nrOfCreatedImages = MutableStateFlow(0)

    fun generateImages(onFinish: () -> Unit) {
        val saveDirectory = configuration.saveDirectory ?: run {
            throw IllegalStateException("Image creation attempted without save directory")
        }

        viewModelScope.launch(Dispatchers.IO) {
            Analytics.imageCreationEvent(configuration)

            var createdImages: List<Uri>
            val durationInMillis = measureTimeMillis {
                val bitmaps =
                    configuration.pattern.getImageGenerator().createBitmaps(configuration)
                createdImages =
                    ImageSaver.saveBitmaps(
                        bitmaps,
                        nrOfCreatedImages,
                        getApplication<Application>().applicationContext,
                        saveDirectory,
                        configuration.format,
                    )
            }
            result = Result(
                configuration.amount,
                configuration.pattern,
                getSaveDirectoryName() ?: "",
                durationInMillis,
                createdImages
            )
            MainScope().launch {
                onFinish()
            }
        }
    }

    private fun getSaveDirectoryName(): String? {
        val saveDirectory = configuration.saveDirectory ?: run {
            return null
        }
        val context = getApplication<Application>().applicationContext
        val saveDirectoryUri = DocumentFile.fromTreeUri(context, saveDirectory)?.uri ?: return null

        return context.contentResolver.query(saveDirectoryUri)?.use { cursor ->
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (columnIndex == -1) return null
            return cursor.getStringOrNull(columnIndex)
        }
    }
}
