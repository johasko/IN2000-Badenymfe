package com.example.badenymfe.userinterface

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.badenymfe.database.ForecastDao
import com.example.badenymfe.database.ForecastDatabase
import com.example.badenymfe.database.entities.LocationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ForecastDatabaseTest {

    private lateinit var forecastDao: ForecastDao
    private lateinit var database: ForecastDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(context, ForecastDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        forecastDao = database.forecastDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertGetAndDeleteLocation() {

        val locations = forecastDao.getLocations()

        CoroutineScope(Dispatchers.IO).launch {
            forecastDao.upsertLocations(
                LocationEntity(
                    59.0, 19.0,
                    "Tromsø"
                )
            )

            assertEquals("Tromsø", locations.value?.get(0)?.title)
        }

    }
}