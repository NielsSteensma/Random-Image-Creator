package com.randomimagecreator.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.randomimagecreator.common.ImageCreatorOptions

object Analytics {
    private const val IMAGE_CREATION_EVENT = "image_creation_event"
    private const val IMAGE_CREATION_EVENT_PARAM_AMOUNT = "amount"
    private const val IMAGE_CREATION_EVENT_PARAM_WIDTH = "width"
    private const val IMAGE_CREATION_EVENT_PARAM_HEIGHT = "height"
    private const val IMAGE_CREATION_EVENT_PARAM_FORMAT = "format"

    /**
     * For debug builds analytics can be null, because I don't want to have google-service.json
     * inside this repo. Therefore the plugin is disabled in build.gradle. Only during CI the file
     * will be decrypted.
     *
     * Putting it in our repo means, if Firebase is wrongly configured, anyone could alter this
     * source code and send fake analytics.
     *
     * Yes, the API key can still be found in our APK. But crawling GitHub is way easier
     * (to automate) than crawling source code of APK's.
     */
    private var analytics: FirebaseAnalytics? = null

    /**
     * Sets up the analytics manager.
     *
     * Note: Make sure to call this in the main activity.
     */
    fun setup() {
        try {
            analytics = Firebase.analytics
        } catch (exception: IllegalStateException) {
            Log.d(
                this::class.simpleName,
                "Plugin is disabled in build script. Ignoring analytics."
            )
        }
    }

    /**
     * Logs an image creation attempt with given set of [imageCreatorOptions].
     */
    fun imageCreationEvent(imageCreatorOptions: ImageCreatorOptions) {
        analytics?.logEvent(IMAGE_CREATION_EVENT, Bundle().apply {
            putInt(IMAGE_CREATION_EVENT_PARAM_AMOUNT, imageCreatorOptions.amount)
            putInt(IMAGE_CREATION_EVENT_PARAM_WIDTH, imageCreatorOptions.width)
            putInt(IMAGE_CREATION_EVENT_PARAM_HEIGHT, imageCreatorOptions.height)
            putString(IMAGE_CREATION_EVENT_PARAM_FORMAT, imageCreatorOptions.format.name)
        })
    }
}
