package com.randomimagecreator.configuration

import androidx.compose.runtime.Composable

@Composable
fun ConfigurationRoute(
    configuration: Configuration,
    onValidConfigurationSubmit: (configuration: Configuration) -> Unit
) {
    ConfigurationScreen(configuration, onValidConfigurationSubmit)
}