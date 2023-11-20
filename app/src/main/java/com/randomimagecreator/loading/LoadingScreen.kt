package com.randomimagecreator.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.randomimagecreator.R
import com.randomimagecreator.common.HeaderScreen
import com.randomimagecreator.configuration.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoadingScreen(numberOfSavedImagesFlow: StateFlow<Int>, numberOfImages: Int) {
    val numberOfSavedImages by numberOfSavedImagesFlow.collectAsState()

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
}

@Preview(showSystemUi = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(MutableStateFlow(1), 12)
}