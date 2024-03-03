package com.randomimagecreator.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

private const val LAST_PERSISTED_SAVE_DIRECTORY = "lastPersistedSaveDirectory"

class MainDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun getLastPersistedSaveDirectory(): String? {
        return dataStore.data.first()[stringPreferencesKey(LAST_PERSISTED_SAVE_DIRECTORY)]
    }

    suspend fun setLastPersistedSaveDirectory(uri: String) {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(LAST_PERSISTED_SAVE_DIRECTORY)] = uri
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MainDataStore? = null

        @Synchronized
        fun getInstance(dataStore: DataStore<Preferences>): MainDataStore =
            INSTANCE ?: MainDataStore(dataStore).also { INSTANCE = it }
    }
}