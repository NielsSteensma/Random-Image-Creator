package com.randomimagecreator

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.randomimagecreator.Destinations.CHOOSE_SAVE_DIRECTORY_ROUTE
import com.randomimagecreator.Destinations.CONFIGURATION_ROUTE
import com.randomimagecreator.Destinations.LOADING_ROUTE
import com.randomimagecreator.Destinations.RESULT_ROUTE
import com.randomimagecreator.choosesavedirectory.ChooseSaveDirectoryRoute
import com.randomimagecreator.configuration.ConfigurationRoute
import com.randomimagecreator.loading.LoadingRoute
import com.randomimagecreator.result.ResultRoute

object Destinations {
    const val CONFIGURATION_ROUTE = "configuration"
    const val CHOOSE_SAVE_DIRECTORY_ROUTE = "choose_save_directory"
    const val LOADING_ROUTE = "loading"
    const val RESULT_ROUTE = "result"
}

@Composable
fun RandomImageCreatorNavHost(navController: NavHostController = rememberNavController()) {
    val viewModel = viewModel<MainViewModel>()
    NavHost(
        navController = navController,
        startDestination = CONFIGURATION_ROUTE
    ) {
        composable(
            CONFIGURATION_ROUTE,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }) {
            ConfigurationRoute(onValidConfigurationSubmit = {
                viewModel.configuration = it
                navController.navigate(CHOOSE_SAVE_DIRECTORY_ROUTE)
            })
        }
        composable(CHOOSE_SAVE_DIRECTORY_ROUTE,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }) {
            ChooseSaveDirectoryRoute(onSaveDirectorySubmit = {
                viewModel.configuration.saveDirectory = it
                navController.navigate(LOADING_ROUTE)
            })
        }
        composable(LOADING_ROUTE,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }) {
            LoadingRoute(viewModel, onLoadingFinished = {
                navController.navigate(RESULT_ROUTE)
            })
        }
        composable(RESULT_ROUTE,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }) {
            ResultRoute(viewModel.result!!, onBackPress = {
                navController.popBackStack(CONFIGURATION_ROUTE, false)
            })
        }
    }
}