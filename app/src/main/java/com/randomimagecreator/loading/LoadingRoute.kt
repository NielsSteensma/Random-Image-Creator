package com.randomimagecreator.loading

import androidx.compose.runtime.Composable
import com.randomimagecreator.MainViewModel

@Composable
fun LoadingRoute(
    viewModel: MainViewModel,
    onLoadingFinished: () -> Unit
) {
    viewModel.generateImages(onLoadingFinished)
    LoadingScreen(viewModel.nrOfCreatedImages, viewModel.configuration.amount)
}