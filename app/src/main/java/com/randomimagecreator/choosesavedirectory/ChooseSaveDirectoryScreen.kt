package com.randomimagecreator.choosesavedirectory

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ChooseSaveDirectoryScreen(onSaveDirectorySubmit: (uri: Uri) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
            uri?.let {
                onSaveDirectorySubmit(it)
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderScreen()
        Spacer(Modifier.weight(1.0f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.choose_save_directory_message),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 0.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                launcher.launch(null)
            }
        ) {
            Text(
                stringResource(R.string.choose_save_directory_button),
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Spacer(Modifier.weight(1.0f))
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChooseSaveDirectoryScreenPreview() {
    ChooseSaveDirectoryScreen {}
}