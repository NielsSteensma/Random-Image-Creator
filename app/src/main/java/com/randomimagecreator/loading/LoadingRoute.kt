package com.randomimagecreator.loading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.randomimagecreator.MainViewModel
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.result.Result

@Composable
fun LoadingRoute(
    viewModel: MainViewModel,
    onLoadingFinished: () -> Unit
) {
    viewModel.generateImages(onLoadingFinished)
    LoadingScreen(viewModel.nrOfCreatedImages, viewModel.configuration.amount)
}