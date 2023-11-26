package com.randomimagecreator.configuration

import ImageFileFormatDropdown
import ImagePatternDropdown
import IntTextField
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
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
fun ConfigurationScreen(onValidConfigurationSubmit: (configuration: Configuration) -> Unit) {
    val configuration by remember { mutableStateOf(Configuration(0, 0, 0)) }
    val (isAmountValid, setIsAmountValid) = remember { mutableStateOf(false) }
    val (amountErrorMessage, setAmountErrorMessage) = remember { mutableStateOf<Int?>(null) }
    val (isWidthValid, setIsWidthValid) = remember { mutableStateOf(false) }
    val (widthErrorMessage, setWidthErrorMessage) = remember { mutableStateOf<Int?>(null) }
    val (isHeightValid, setIsHeightValid) = remember { mutableStateOf(false) }
    val (heightErrorMessage, setHeightErrorMessage) = remember { mutableStateOf<Int?>(null) }

    Column {
        HeaderScreen()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Spacer(Modifier.weight(1.0f))
            IntTextField(
                value = configuration.amount,
                isError = isAmountValid,
                label = {
                    var label = stringResource(R.string.image_creator_option_amount_hint)
                    amountErrorMessage?.let {
                        label = "$label (${stringResource(it).lowercase()})"
                    }
                    Text(label, color = MaterialTheme.colors.primary)
                },
                onValueChange = {
                    if (isAmountValid) {
                        setAmountErrorMessage(null)
                        setIsAmountValid(false)
                    }
                    configuration.amount = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            IntTextField(
                value = configuration.width,
                isError = isWidthValid,
                label = {
                    var label = stringResource(R.string.image_creator_option_width_hint)
                    widthErrorMessage?.let {
                        label = "$label (${stringResource(it).lowercase()})"
                    }
                    Text(label, color = MaterialTheme.colors.primary)
                },
                onValueChange = {
                    if (isWidthValid) {
                        setWidthErrorMessage(null)
                        setIsWidthValid(false)
                    }
                    configuration.width = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            IntTextField(
                value = configuration.height,
                isError = isHeightValid,
                label = {
                    var label = stringResource(R.string.image_creator_option_height_hint)
                    heightErrorMessage?.let {
                        label = "$label (${stringResource(it).lowercase()})"
                    }
                    Text(label, color = MaterialTheme.colors.primary)
                },
                onValueChange = {
                    if (isHeightValid) {
                        setHeightErrorMessage(null)
                        setIsHeightValid(false)
                    }
                    configuration.height = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ImageFileFormatDropdown(
                value = configuration.format,
                label = {
                    Text(
                        stringResource(R.string.image_creator_option_image_file_format_hint),
                        color = MaterialTheme.colors.primary
                    )
                },
                onValueChange = {
                    configuration.format = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ImagePatternDropdown(
                value = configuration.pattern,
                label = {
                    Text(
                        stringResource(R.string.image_creator_option_pattern_hint),
                        color = MaterialTheme.colors.primary
                    )
                },
                onValueChange = {
                    configuration.pattern = it
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    if (configuration.validator.isValid) {
                        onValidConfigurationSubmit(configuration)
                    } else {
                        setIsAmountValid(configuration.validator.amountValidationMessage() != null)
                        setAmountErrorMessage(configuration.validator.amountValidationMessage())
                        setIsWidthValid(configuration.validator.widthValidationMessage() != null)
                        setWidthErrorMessage(configuration.validator.widthValidationMessage())
                        setIsHeightValid(configuration.validator.heightValidationMessage() != null)
                        setHeightErrorMessage(configuration.validator.heightValidationMessage())
                    }
                }
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
}

@Preview(showSystemUi = true)
@Composable
fun ConfigurationScreenPreview() {
    ConfigurationScreen {}
}