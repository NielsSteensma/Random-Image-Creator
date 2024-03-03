package com.randomimagecreator.choosesavedirectory

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.MainDataStore
import com.randomimagecreator.common.extensions.contentResolver
import kotlinx.coroutines.launch

/**
 * ViewModel for [ChooseSaveDirectoryFragment]. Groups logic for save directory persistence.
 * */
class ChooseSaveDirectoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = MainDataStore.getInstance(application)
    suspend fun getLastPersistedSaveDirectory(): Uri? {
        val saveDirectory = dataStore.getLastPersistedSaveDirectory() ?: return null
        return contentResolver.persistedUriPermissions.first {
            it.uri.toString() == saveDirectory && it.isWritePermission
        }.uri
    }

    fun persistSaveDirectory(uri: Uri) {
        val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, flags)
        viewModelScope.launch {
            dataStore.setLastPersistedSaveDirectory(uri.toString())
        }
    }
}
