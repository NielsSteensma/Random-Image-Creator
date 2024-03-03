package com.randomimagecreator.choosesavedirectory

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.randomimagecreator.common.MainDataStore
import kotlinx.coroutines.launch


/**
 * ViewModel for [ChooseSaveDirectoryFragment]. Groups logic for save directory persistence.
 * */
class ChooseSaveDirectoryViewModel(application: Application) : AndroidViewModel(application) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "main")
    private val dataStore = MainDataStore.getInstance(application.dataStore)
    private val contentResolver: ContentResolver
        get() {
            return getApplication<Application>().contentResolver
        }

    suspend fun getLastPersistedSaveDirectory(): Uri? {
        val lastPersistedSaveDirectoryName = dataStore.getLastPersistedSaveDirectory() ?: return null
        return contentResolver.persistedUriPermissions.first {
            it.uri.toString() == lastPersistedSaveDirectoryName && it.isWritePermission
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
