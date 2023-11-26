package com.randomimagecreator.configuration

import androidx.compose.runtime.Composable

@Composable
fun ConfigurationRoute(
    onValidConfigurationSubmit: (configuration: Configuration) -> Unit
) {
    ConfigurationScreen(Configuration(), onValidConfigurationSubmit)
}