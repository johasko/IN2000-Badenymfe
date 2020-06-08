package com.example.badenymfe.userinterface


import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.rule.ActivityTestRule
import com.example.badenymfe.BadenymfeApplication
import com.example.badenymfe.R
import com.google.android.gms.maps.model.LatLng
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MapsActivityTest {

    lateinit var scenario: ActivityScenario<MapsActivity>
    private val databaseName = "ForecastDatabase"
    
    @get:Rule
    var intentsRule = ActivityTestRule(MapsActivity::class.java)

    @Before
    fun click_on_map(){
        //Deletes database so that the tests receives expected data
        getApplicationContext<BadenymfeApplication>().deleteDatabase(databaseName)

        // clicks on the map. Sleeps in order to let the map get ready to react for clicks.
        Thread.sleep(50)
        onView(withId(R.id.map)).perform(click())
        Thread.sleep(50)
    }


    @Test
    fun check_click_on_map_displays_plus_button() {
        // Check that the add location button (plus sign) is displayed
        onView(withId(R.id.button_add_location)).check(matches(isDisplayed()))
    }


    @Test
    fun check_adding_valid_place_navigates_to_locations(){
        //Arrange
        val position = LatLng(68.51659841117424, 17.485356777906418)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)
        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity{
            method.invoke(intentsRule.activity, position)
        }

        onView(withId(R.id.button_add_location)).perform(click())

        //Assert
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun check_adding_valid_place_get_hour_by_hour(){
        //Arrange
        val position = LatLng(68.51659841117424, 17.485356777906418)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }

        onView(withId(R.id.button_add_location)).perform(click())
        Thread.sleep(50)
        
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Assert
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun check_adding_valid_place_not_showing_talking_jelly(){
        //Arrange
        val position = LatLng(68.51659841117424, 17.485356777906418)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }

        onView(withId(R.id.button_add_location)).perform(click())


        Thread.sleep(500)
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

            //Sleeps so that the data is retrieved when we check if the talking jelly is gone
        Thread.sleep(800)

        //Assert
        onView(withId(R.id.talking_jelly_hour)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        scenario.close()
    }

    @Test
    fun check_adding_invalid_place_get_talking_jelly(){
        //Arrange
        val position = LatLng(61.798, 11.11)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }

        onView(withId(R.id.button_add_location)).perform(click())
        Thread.sleep(50)

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //The app shows the talking jelly if the position is valid, but it has not retrieved the data yet.
        //Sleeps so that adding valid coordinates does not pass this test
        Thread.sleep(300)

        //Assert
        onView(withText("Vi har dessverre \n ingen data for denne \n posisjonen")).check(matches(
            isDisplayed()))

        scenario.close()
    }

    @Test
    fun check_adding_valid_place_get_forecast(){
        //Arrange
        val position = LatLng(68.51659841117424, 17.485356777906418)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }
        onView(withId(R.id.button_add_location)).perform(click())

        Thread.sleep(50)
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(5000)
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(200)

        //Assert
        onView(withId(R.id.temperature_textview)).check(matches(isDisplayed()))

        scenario.close()

    }

    @Test
    fun check_adding_valid_place_saves_correct_LatLng(){
        //Arrange
        val latitude = 68.51659841117424
        val longitude = 17.485356777906418
        val lat = 68.517
        val long = 17.485
        val position = LatLng(latitude, longitude)
        val positionString = "lat $lat, lon $long"
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }
        onView(withId(R.id.button_add_location)).perform(click())

        Thread.sleep(200)

        //Assert
        onView(withId(R.id.text_location)).check(matches(withText(positionString)))

        scenario.close()

    }

    @Test
    fun check_deleting_location_removes_it_from_favourites(){
        //Arrange
        val latitude = 68.51659841117424
        val longitude = 17.485356777906418
        val lat = 68.517
        val long = 17.485
        val position = LatLng(latitude, longitude)
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }
        onView(withId(R.id.button_add_location)).perform(click())

        Thread.sleep(200)

        onView(withId(R.id.delete_button)).perform(click())

        Thread.sleep(50)
        onView(withText("YEAH BUDDY!")).perform(click())

        //Assert
        onView(withId(R.id.text_location)).check(doesNotExist())

        scenario.close()
    }

    @Test
    fun check_regretting_delete_not_deleting_location(){
        //Arrange
        val latitude = 68.51659841117424
        val longitude = 17.485356777906418
        val lat = 68.517
        val long = 17.485
        val position = LatLng(latitude, longitude)
        val positionString = "lat $lat, lon $long"
        val intent = Intent(ApplicationProvider.getApplicationContext(), MapsActivity::class.java)

        scenario = ActivityScenario.launch(intent)

        val method = MapsActivity::class.java
            .getDeclaredMethod("placeMarkerOnMap", LatLng::class.java)
        method.isAccessible = true

        //Act
        scenario.onActivity {
            method.invoke(intentsRule.activity, position)
        }
        onView(withId(R.id.button_add_location)).perform(click())

        Thread.sleep(200)

        onView(withId(R.id.delete_button)).perform(click())

        Thread.sleep(50)
        onView(withText("HELL NO!")).perform(click())

        //Assert
        onView(withId(R.id.text_location)).check(matches(withText(positionString)))

        scenario.close()
    }

    @After
    fun delete_database(){
        getApplicationContext<BadenymfeApplication>().deleteDatabase(databaseName)
    }
 }


