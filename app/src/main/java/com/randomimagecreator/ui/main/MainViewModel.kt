package com.randomimagecreator.ui.main

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.ImageCreatorOptions
import com.randomimagecreator.creators.SolidColorCreator
import com.randomimagecreator.helpers.ImageSaver
import com.randomimagecreator.helpers.toString
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    val amount = MutableLiveData<Int>()
    val width = MutableLiveData<Int>()
    val height = MutableLiveData<Int>()
    val state = MutableLiveData<State>()
    var createdImageUris = ArrayList<Uri>()

    /**
     * Returns an object of [ImageCreatorOptions].
     *
     * Make sure to validate the form before calling this method. Otherwise [NullPointerException]
     * might be thrown.
     */
    val imageCreatorOptions
        get() = ImageCreatorOptions(
            amount.value!!,
            width.value!!,
            height.value!!,
            Calendar.getInstance().time.toString("dd-MM-YY hhmmss")
        )


    /**
     * Returns a [Boolean] that indicates if the image creation form is valid.
     */
    private fun isFormValid(): Boolean {
        if (amount.value == null || amount.value == 0) {
            return false
        }
        if (width.value == null || width.value == 0) {
            return false
        }
        if (height.value == null || height.value == 0) {
            return false
        }
        return true
    }

    fun onUserWantsToCreateImages(contentResolver: ContentResolver) {
        if (!isFormValid()) {
            state.postValue(State.INVALID_FORM_FOUND)
            return
        }

        viewModelScope.launch {
            state.postValue(State.STARTED_CREATING_IMAGES)

            val options = imageCreatorOptions
            val bitmaps = SolidColorCreator().createBitmaps(options)

            createdImageUris =
                ImageSaver.saveBitmaps(bitmaps, contentResolver, options.storageDirectory)
            state.postValue(State.FINISHED_CREATING_IMAGES)
        }
    }

    /**
     *  Represents all possible states the [MainViewModel] can have.
     */
    enum class State {
        INITIAL,
        INVALID_FORM_FOUND,
        STARTED_CREATING_IMAGES,
        FINISHED_CREATING_IMAGES
    }
}
