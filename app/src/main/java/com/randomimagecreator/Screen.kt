package com.randomimagecreator

/**
 *  Groups all screens of the app
 */
sealed class Screen {
    /**
     * Entry screen where the user can configure the image creation.
     */
    data object Configuration : Screen()

    /**
     * Screen where user can choose the directory to save the images in.
     */
    data object ChooseSaveDirectory : Screen()

    /**
     * Screen showing image creation is in progress.
     */
    data object Loading : Screen()

    /**
     * Screen showing an error occurred during image generation.
     */
    data object Error : Screen()

    /**
     * Screen showing images that got created.
     */
    data object Result : Screen()
}