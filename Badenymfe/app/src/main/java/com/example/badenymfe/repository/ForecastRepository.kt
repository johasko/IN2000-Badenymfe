package com.example.badenymfe.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.badenymfe.database.ForecastDatabase
import com.example.badenymfe.database.entities.LocationEntity
import com.example.badenymfe.database.entities.asDomainModels
import com.example.badenymfe.domain.OceanForecast
import com.example.badenymfe.domain.Location
import com.example.badenymfe.domain.WeatherForecast
import com.example.badenymfe.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import timber.log.Timber


/**
 * Handles forecast specific data requests from view models.
 */
class ForecastRepository(private val database: ForecastDatabase) {

    /**
     * Geodetic latitude.
     */
    var latitude = 0.0

    /**
     * Geodetic longitude.
     */
    var longitude = 0.0

    /**
     * Local time and day.
     */
    var timeAndDay: DateTime = DateTime.now()

    /**
     * List of available time- and days
     */
    val timeAndDays: LiveData<List<DateTime>>
    get() {
        Timber.d("Fetching time and day from database.")
        return Transformations.map(
            database.forecastDao.getTimeAndDays(latitude, longitude)
        ) { timeAndDay ->
            timeAndDay.map {
                DateTime(it)
            }
        }
    }

    /**
     * List of locations as domain models.
     */
    val locations: LiveData<List<Location>>
    get() {
        Timber.d("Fetching locations from database.")
        return Transformations.map(
            database.forecastDao.getLocations()
        ) { entities ->
            entities.asDomainModels()
        }
    }

    /**
     * Ocean forecast as domain models.
     */
    val oceanForecast: LiveData<OceanForecast>
    get() {
        Timber.d("Fetching ocean forecast from database.")
        return Transformations.map(
            database.forecastDao.getOceanForecast(
                latitude,
                longitude,
                timeAndDay.toDateTime(DateTimeZone.UTC).millis
            )
        ) {
            it?.asDomainModel()
        }
    }

    /**
     * Weather forecast as domain models.
     */
    val weatherForecast: LiveData<WeatherForecast>
    get() {
        Timber.d("Fetching weather forecast from database.")
        return Transformations.map(
            database.forecastDao.getWeatherForecast(
                latitude,
                longitude,
                timeAndDay.toDateTime(DateTimeZone.UTC).millis
            )
        ) {
            it?.asDomainModel()
        }
    }

    /**
     * Insert forecast location.
     * This function will be used when adding a new location from the map.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @param title user defined title.
     */
    suspend fun insertLocation(lat: Double, lon: Double, title: String) {
        Timber.d("Inserting forecast data from location lat: $lat, lon: $lon")
        withContext(Dispatchers.IO) {
            database.forecastDao.upsertLocations(LocationEntity(lat, lon, title))
            val oceanForecast = Network.metApi.getOceanForecastAsync(lat, lon).await()
            oceanForecast.asDatabaseEntities(lat, lon)?.forEach { entity ->
                database.forecastDao.upsertOceanForecast(entity)
            }
            val weatherForecast = Network.metApi.getWeatherForecastAsync(lat, lon).await()
            weatherForecast.asDatabaseEntities(lat, lon)?.forEach { entity ->
                database.forecastDao.upsertWeatherForecast(entity)
            }
        }
    }

    /**
     * Delete forecast location.
     * This function is used when user want to delete a forecast location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     */
    suspend fun deleteLocation(lat: Double, lon: Double) {
        Timber.d("Deleting forecast data from location lat: $lat, lon: $lon")
        withContext(Dispatchers.IO) {
            database.forecastDao.deleteLocation(lat, lon)
            database.forecastDao.deleteOceanForecast(lat, lon)
            database.forecastDao.deleteWeatherForecast(lat, lon)
        }
    }
}
