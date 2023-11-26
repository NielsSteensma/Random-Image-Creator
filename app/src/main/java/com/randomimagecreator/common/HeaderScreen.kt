package com.randomimagecreator.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.randomimagecreator.R

@Composable
fun HeaderScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0, 68, 85))
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = ""
            )
        }
        Row {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HeaderScreenPreview() {
    HeaderScreen()
}