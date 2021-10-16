package com.randomimagecreator.ui.main

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.ImageCreatorOptions
import com.randomimagecreator.analytics.AnalyticsManager
import com.randomimagecreator.creators.SolidColorCreator
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.helpers.toString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    val imageCreatorOptions = MutableLiveData(ImageCreatorOptions())
    val state = MutableLiveData<State>()
    var createdImageUris = ArrayList<Uri>()
    lateinit var saveDirectory: String

    fun onUserWantsToCreateImages(contentResolver: ContentResolver) {
        val options = imageCreatorOptions.value
        if (options == null || !options.isValid()) {
            state.postValue(State.INVALID_FORM_FOUND)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.STARTED_CREATING_IMAGES)

            AnalyticsManager.logImageCreationEvent(options)
            val bitmaps = SolidColorCreator().createBitmaps(options)
            saveDirectory = Calendar.getInstance().time.toString("dd-MM-YY hhmmss")
            createdImageUris = ImageSaver.saveBitmaps(bitmaps, contentResolver, saveDirectory)
            state.postValue(State.FINISHED_CREATING_IMAGES)
        }
    }

    /**
     *  Represents all possible states the [MainViewModel] can have.
     */
    enum class State {
        INVALID_FORM_FOUND,
        STARTED_CREATING_IMAGES,
        FINISHED_CREATING_IMAGES
    }
}
