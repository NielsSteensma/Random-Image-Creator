package com.randomimagecreator.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.randomimagecreator.R
import com.randomimagecreator.configuration.ImagePattern

@Composable
fun ResultScreen(result: Result) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    stringResource(R.string.created_images_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Text(stringResource(R.string.created_images_amount), fontSize = 16.sp)
                Spacer(Modifier.weight(1.0f))
                Text(result.amount.toString(), fontSize = 16.sp)
            }
            Row {
                Text(stringResource(R.string.created_images_pattern), fontSize = 16.sp)
                Spacer(Modifier.weight(1.0f))
                Text(result.pattern.toString(), fontSize = 16.sp)
            }
            Row {
                Text(stringResource(R.string.created_images_directory), fontSize = 16.sp)
                Spacer(Modifier.weight(1.0f))
                Text(result.directoryName, fontSize = 16.sp)
            }
            Row {
                Text(stringResource(R.string.created_images_duration), fontSize = 16.sp)
                Spacer(Modifier.weight(1.0f))
                Text("10", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            ImagesGrid(imageUris = result.uris)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ResultScreenPreview() {
    val result = Result(
        10,
        ImagePattern.SOLID,
        "directory",
        10,
        listOf()
    )
    ResultScreen(result)
}