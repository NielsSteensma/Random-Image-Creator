package com.randomimagecreator.choosesavedirectory

import android.net.Uri
import androidx.compose.runtime.Composable

@Composable
fun ChooseSaveDirectoryRoute(
    onSaveDirectorySubmit: (uri: Uri) -> Unit
) {
    ChooseSaveDirectoryScreen(onSaveDirectorySubmit)
}