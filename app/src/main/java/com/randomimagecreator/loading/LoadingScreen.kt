package com.randomimagecreator.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.randomimagecreator.R
import com.randomimagecreator.common.HeaderScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(numberOfSavedImagesFlow: StateFlow<Int>, numberOfImages: Int) {
    val numberOfSavedImages by numberOfSavedImagesFlow.collectAsState()
    val scope = rememberCoroutineScope()
    val (isSnackBarVisible, setSnackBarIsVisible) = remember { mutableStateOf(false) }
    BackHandler {
        scope.launch {
            setSnackBarIsVisible(true)
        }
    }
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderScreen()
            Spacer(Modifier.weight(1.0f))
            CircularProgressIndicator(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                color = Color(0, 68, 85)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(
                        R.string.loading_saved_amount,
                        numberOfSavedImages,
                        numberOfImages
                    ), fontSize = 18.sp
                )
            }
            Spacer(Modifier.weight(1.0f))
        }
        if (isSnackBarVisible) {
            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                GeneratingImagesSnackBar()
            }
        }
    }
}

@Composable
private fun GeneratingImagesSnackBar() {
    Snackbar(backgroundColor = Color.Gray) {
        Text(
            text = "Generating images, please wait...",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(MutableStateFlow(1), 12)
}