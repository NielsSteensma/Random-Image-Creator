package com.randomimagecreator.configuration

import CpumarketImageFileFormatDropdown
import CpumarketImagePatternDropdown
import CpumarketIntTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.randomimagecreator.R
import com.randomimagecreator.common.HeaderScreen

@Composable
fun ConfigurationScreen(
    onValidConfigurationSubmit: (configuration: Configuration) -> Unit,
) {
    val configuration by remember { mutableStateOf(Configuration(0, 0, 0)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderScreen()
        Spacer(Modifier.weight(1.0f))
        CpumarketIntTextField(
            value = configuration.amount,
            label = { Text(stringResource(R.string.image_creator_option_amount_hint)) },
            onValueChange = {
                configuration.amount = it
            })
        Spacer(modifier = Modifier.height(10.dp))
        CpumarketIntTextField(
            value = configuration.width,
            label = { Text(stringResource(R.string.image_creator_option_width_hint)) },
            onValueChange = {
                configuration.width = it
            })
        Spacer(modifier = Modifier.height(10.dp))
        CpumarketIntTextField(
            value = configuration.height,
            label = { Text(stringResource(R.string.image_creator_option_height_hint)) },
            onValueChange = {
                configuration.height = it
            })
        Spacer(modifier = Modifier.height(10.dp))
        CpumarketImageFileFormatDropdown(
            value = configuration.format,
            label = { Text(stringResource(R.string.image_creator_option_image_file_format_hint)) },
            onValueChange = {
                configuration.format = it
            })
        Spacer(modifier = Modifier.height(10.dp))
        CpumarketImagePatternDropdown(
            value = configuration.pattern,
            label = { Text(stringResource(R.string.image_creator_option_pattern_hint)) },
            onValueChange = {
                configuration.pattern = it
            })
        Button(
            onClick = { onValidConfigurationSubmit(configuration) }
        ) {
            Text(
                stringResource(R.string.image_creator_button_create),
                fontSize = 18.sp,
                color = Color.White
            )
        }
        Spacer(Modifier.weight(1.0f))
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConfigurationScreenPreview() {
    ConfigurationScreen {}
}