package com.randomimagecreator.helpers

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import java.lang.reflect.Field

/**
 * Helper class that allows to wait until the provided view appears.
 *
 * @param viewMatcher The view to wait for.
 * @param idleMatcher The condition when the view becomes idle.
 */
private class ViewIdlingResource(
    private val viewMatcher: Matcher<View?>?,
    private val idleMatcher: Matcher<View?>?
) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun isIdleNow(): Boolean {
        val view = getView(viewMatcher)
        val isIdle = idleMatcher?.matches(view) ?: false
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

    override fun getName() = "$this ${viewMatcher.toString()}"

    private fun getView(viewMatcher: Matcher<View?>?): View? {
        return try {
            val viewInteraction = onView(viewMatcher)
            val finderField: Field? = viewInteraction.javaClass.getDeclaredField("viewFinder")
            finderField?.isAccessible = true
            val finder = finderField?.get(viewInteraction) as ViewFinder
            finder.view
        } catch (exception: Exception) {
            null
        }
    }
}

/**
 * Waits for 30 seconds until the given view is displayed.
 * If it takes any longer an [androidx.test.espresso.IdlingResourceTimeoutException] will be thrown.
 *
 * @param matcher The matcher to wait for.
 */
fun waitForViewToBeDisplayed(matcher: Matcher<View?>) {
    val idlingResource = ViewIdlingResource(matcher, isDisplayed())
    try {
        IdlingRegistry.getInstance().register(idlingResource)
        // This way we can trigger the idler.
        onView(withId(0)).check(doesNotExist())
    } finally {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
