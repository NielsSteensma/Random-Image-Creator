package com.randomimagecreator.ui.shared

/**
 *  Groups all states in which the app can be.
 */
sealed class State {
    /**
     * Entry screen where the user can configure the image creation.
     */
    object Initial : State()
    /**
     * User clicked on create button and image creation configuration was found invalid.
     */
    object SubmitConfigInvalid : State()
    /**
     * User clicked on create button and image creation configuration was found valid.
     */
    object SubmitConfigValid : State()
    /**
     * User choose the directory to save the images in.
     */
    object SubmitSaveDirectory : State()
    /**
     * Images were created.
     */
    data class FinishedCreatingImages(val duration: Long) : State()
}