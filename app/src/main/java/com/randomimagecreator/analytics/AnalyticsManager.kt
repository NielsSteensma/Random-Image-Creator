package com.randomimagecreator.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.randomimagecreator.ImageCreatorOptions

/**
 * Manager for analytics
 */
object AnalyticsManager {
    private lateinit var analytics: FirebaseAnalytics

    private const val IMAGE_CREATION_EVENT = "image_creation_event"
    private const val IMAGE_CREATION_EVENT_PARAM_AMOUNT = "amount"
    private const val IMAGE_CREATION_EVENT_PARAM_WIDTH = "width"
    private const val IMAGE_CREATION_EVENT_PARAM_HEIGHT = "height"

    /**
     * Sets up the analytics manager
     *
     * Note: Make sure to call this in the main activity
     */
    fun setup(){
        analytics = Firebase.analytics
    }

    /**
     * Logs an image creation attempt with given set of [imageCreatorOptions]
     */
    fun logImageCreationEvent(imageCreatorOptions: ImageCreatorOptions) {
        analytics.logEvent(IMAGE_CREATION_EVENT, Bundle().apply {
            putInt(IMAGE_CREATION_EVENT_PARAM_AMOUNT, imageCreatorOptions.amount)
            putInt(IMAGE_CREATION_EVENT_PARAM_WIDTH, imageCreatorOptions.width)
            putInt(IMAGE_CREATION_EVENT_PARAM_HEIGHT, imageCreatorOptions.height)
        })
    }
}
