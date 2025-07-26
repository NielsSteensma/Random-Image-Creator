package com.randomimagecreator.common.extensions

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import androidx.lifecycle.AndroidViewModel


val AndroidViewModel.contentResolver
    get(): ContentResolver {
        return this.getApplication<Application>().contentResolver
    }

val AndroidViewModel.context
    get(): Context {
        return this.getApplication()
    }