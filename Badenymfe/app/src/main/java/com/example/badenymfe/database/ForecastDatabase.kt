package com.example.badenymfe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.badenymfe.database.entities.LocationEntity
import com.example.badenymfe.database.entities.OceanForecastEntity
import com.example.badenymfe.database.entities.WeatherForecastEntity

/**
 * An abstract class defining our custom database.
 * Version has to be incremented after doing changes to the database,
 * this is in order to tell room to build us a new one.
 */
@Database(
    entities = [LocationEntity::class, OceanForecastEntity::class, WeatherForecastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ForecastDatabase : RoomDatabase() {
    abstract val forecastDao: ForecastDao
}

private lateinit var INSTANCE: ForecastDatabase

/**
 * Provides a thread safe reference to our database.
 * @param context application context.
 * @return our forecast database
 */
fun getForecastDatabase(context: Context) : ForecastDatabase {
    synchronized(ForecastDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java,
                "ForecastDatabase"
            ).build()
        }
    }
    return INSTANCE
}


