package com.randomimagecreator

/**
 *  Groups all states in which the app can be.
 */
sealed class State {
    /**
     * Entry screen where the user can configure the image creation.
     */
    data object Initial : State()
    
    /**
     * User clicked on create button and image creation configuration was found invalid.
     */
    data object SubmittedConfigurationInvalid : State()
    /**
     * User clicked on create button and image creation configuration was found valid.
     */
    data object SubmittedConfigurationValid : State()

    /**
     * User choose the directory to save the images in.
     */
    data object SubmitSaveDirectory : State()

    /**
     * Image creation is in progress.
     */
    data object CreatingImages : State()

    /**
     * Images were created.
     */
    data class FinishedCreatingImages(val durationMilliseconds: Long) : State()
}