package com.randomimagecreator.choosesavedirectory

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.randomimagecreator.MainViewModel

@Composable
fun ChooseSaveDirectoryRoute(
    onSaveDirectorySubmit: (uri: Uri) -> Unit
) {
    ChooseSaveDirectoryScreen(onSaveDirectorySubmit)
}