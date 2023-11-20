package com.randomimagecreator.result

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.randomimagecreator.MainViewModel
import com.randomimagecreator.configuration.Configuration

@Composable
fun ResultRoute(result: Result) {
    ResultScreen(result)
}