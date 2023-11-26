package com.randomimagecreator.result

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun ResultRoute(result: Result, onBackPress: () -> Unit) {
    BackHandler {
        onBackPress()
    }
    ResultScreen(result)
}