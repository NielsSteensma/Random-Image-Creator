package com.randomimagecreator.ui.shared

/**
 *  Groups all states in which the app can be.
 */
enum class State {
    /**
     * Entry screen where the user can configure the image creation.
     */
    INITIAL,

    /**
     * User clicked on create button and image creation configuration was found invalid.
     */
    SUBMIT_CONFIG_INVALID,

    /**
     * User clicked on create button and image creation configuration was found valid.
     */
    SUBMIT_CONFIG_VALID,

    /**
     * User choose the directory to save the images in.
     */
    SUBMIT_SAVE_DIRECTORY,

    /**
     * Images were created.
     */
    FINISHED_CREATING_IMAGES
}