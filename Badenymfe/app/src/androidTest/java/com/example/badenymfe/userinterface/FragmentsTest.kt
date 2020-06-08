package com.example.badenymfe.userinterface

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.badenymfe.R
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FragmentsTest {
    @Test fun testSettingsFragment() {
        launchFragmentInContainer<SettingsFragment>()
        onView(withId(R.id.textView)).check(matches(withText(containsString("Settings"))))
    }
    @Test fun testAboutFragment() {
        launchFragmentInContainer<AboutFragment>()
        onView(withId(R.id.rulesText)).check(matches(withText(containsString("Badenymfe bruker informasjon fra Meterologisk Institutt"))))
    }
    @Test fun testHourByHourFragment() {
        launchFragmentInContainer<HourByHourFragment>()
        onView(withId(R.id.recycler_view)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test fun testForecastFragment() {
        launchFragmentInContainer<ForecastFragment>()

        onView(withId(R.id.temperature_textview)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.windSpeed_textview)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.jellyfishProb_textview)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.seaDepth_textview)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.waveHeight_textview)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.seaTemperature_textview)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.windDescription_textview)).check(matches(ViewMatchers.isDisplayed()))
    }
    @Test fun testLocationsFragment() {
        launchFragmentInContainer<LocationsFragment>()
        onView(withId(R.id.recycler_view)).check(matches(ViewMatchers.isDisplayed()))
    }


}

