package com.tomtom.sdk.examples.maps

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.tomtom.sdk.examples.MainMenu
import com.tomtom.sdk.examples.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Test
    fun test_on_map_display_click_is_navigated_to_map_examples_view() {
        ActivityScenario.launch(MainMenu::class.java).use {
            Espresso.onView(ViewMatchers.withId(R.id.button7))
                .perform(ViewActions.scrollTo(), ViewActions.click())
            Espresso.onView(ViewMatchers.withId(R.id.map_examples))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    @Test
    fun test_on_try_it_click_is_navigated_to_configurable_map_view() {
        ActivityScenario.launch(MapExamplesActivity::class.java).use {
            Espresso.onView(ViewMatchers.withId(R.id.try_it_layout_button))
                .perform(ViewActions.scrollTo(), ViewActions.click())
            Espresso.onView(ViewMatchers.withId(R.id.configurable_map_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    @Test
    fun test_on_go_back_click_in_configurable_map_view_is_navigated_to_map_examples_view() {
        ActivityScenario.launch(MapExamplesActivity::class.java).use {
            Espresso.onView(ViewMatchers.withId(R.id.try_it_layout_button))
                .perform(ViewActions.scrollTo(), ViewActions.click())
            deny_permission_in_pop_up()
            Espresso.onView(ViewMatchers.withId(R.id.go_back_image_button))
                .perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withId(R.id.map_examples))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    private fun deny_permission_in_pop_up() {
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val denyPermissions = uiDevice.wait(Until.findObject(By.text(
            when (Build.VERSION.SDK_INT) {
                in MapLoadingTest.SDK_VERSION_24..MapLoadingTest.SDK_VERSION_28 -> MapLoadingTest.DENY_TEXT_SDK_24_TO_28
                else -> MapLoadingTest.DENY_TEXT_DEFAULT
            }
        )), MapLoadingTest.TIMEOUT_10
        )
        denyPermissions.click()
    }
}