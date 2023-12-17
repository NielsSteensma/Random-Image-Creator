package com.randomimagecreator.loading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.randomimagecreator.MainViewModel

@Composable
fun LoadingRoute(
    viewModel: MainViewModel,
    onLoadingFinished: () -> Unit
) {
    val isLoadingFinishedState = viewModel.isLoadingFinished.collectAsState(false)
    if (isLoadingFinishedState.value) {
        onLoadingFinished()
    } else {
        LoadingScreen(viewModel.nrOfCreatedImages, viewModel.configuration.amount)
    }
}