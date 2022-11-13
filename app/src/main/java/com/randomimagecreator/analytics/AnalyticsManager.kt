package com.randomimagecreator.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.randomimagecreator.BuildConfig
import com.randomimagecreator.common.ImageCreatorOptions

/**
 * Manager for analytics.
 */
object AnalyticsManager {
    private lateinit var analytics: FirebaseAnalytics
    private const val IMAGE_CREATION_EVENT = "image_creation_event"
    private const val IMAGE_CREATION_EVENT_PARAM_AMOUNT = "amount"
    private const val IMAGE_CREATION_EVENT_PARAM_WIDTH = "width"
    private const val IMAGE_CREATION_EVENT_PARAM_HEIGHT = "height"
    private const val IMAGE_CREATION_EVENT_PARAM_FORMAT = "format"


    /**
     * Returns a boolean indicating if analytics is enabled
     *
     * For debug builds it's disabled, because I don't want to have google-service.json inside
     * this repo.
     *
     * Putting it in our repo means, if Firebase is wrongly configured, anyone could alter this
     * source code and send fake crashes / analytics.
     *
     * Yes, Firebase API key can still be found in our APK. But crawling GitHub is way easier
     * (to automate) than crawling source code of APK's.
     */
    private val analyticsDisabled = BuildConfig.DEBUG

    /**
     * Sets up the analytics manager
     *
     * Note: Make sure to call this in the main activity
     */
    fun setup() {
        if (analyticsDisabled) { return }
        analytics = Firebase.analytics
    }

    /**
     * Logs an image creation attempt with given set of [imageCreatorOptions]
     */
    fun logImageCreationEvent(imageCreatorOptions: ImageCreatorOptions) {
        if (analyticsDisabled) { return }
        analytics.logEvent(IMAGE_CREATION_EVENT, Bundle().apply {
            putInt(IMAGE_CREATION_EVENT_PARAM_AMOUNT, imageCreatorOptions.amount)
            putInt(IMAGE_CREATION_EVENT_PARAM_WIDTH, imageCreatorOptions.width)
            putInt(IMAGE_CREATION_EVENT_PARAM_HEIGHT, imageCreatorOptions.height)
            putString(IMAGE_CREATION_EVENT_PARAM_FORMAT, imageCreatorOptions.format.name)
        })
    }
}
