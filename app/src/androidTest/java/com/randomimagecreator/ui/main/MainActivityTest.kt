package com.randomimagecreator.ui.main

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.randomimagecreator.R
import com.randomimagecreator.helpers.waitForViewToBeDisplayed
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTests {
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun imageCreation_GivenValidImageCreationOptions_CreatesImages() {
        activityTestRule.launchActivity(null)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)

        val amount = 5
        val width = 5
        val height = 5

        onView(withId(R.id.image_creator_option_amount)).perform(typeText(amount.toString()), closeSoftKeyboard())
        onView(withId(R.id.image_creator_option_width)).perform(typeText(width.toString()), closeSoftKeyboard())
        onView(withId(R.id.image_creator_option_height)).perform(typeText(height.toString()), closeSoftKeyboard())
        onView(withId(R.id.image_creator_button_create)).perform(click())

        waitForViewToBeDisplayed(withId(R.id.recyclerview_created_images))
    }
}
