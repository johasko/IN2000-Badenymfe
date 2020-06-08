package com.example.badenymfe.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.badenymfe.database.entities.LocationEntity
import com.example.badenymfe.database.entities.OceanForecastEntity
import com.example.badenymfe.database.entities.WeatherForecastEntity
import org.joda.time.DateTime

/**
 * Defines functions for accessing the forecast database.
 */
@Dao
interface ForecastDao {
    /**
     * Get a list of all saved forecast locations.
     * @return live data containing a list of available forecast location entities.
     */
    @Query("SELECT * FROM LocationEntity")
    fun getLocations() : LiveData<List<LocationEntity>>

    /**
     * Get a list of all saved forecast locations.
     * This method cannot be called on the main thread.
     * @return list of available forecast location entities.
     */
    @Query("SELECT * FROM LocationEntity")
    fun getLocationsSynchronous() : List<LocationEntity>

    /**
     * Checks if ocean forecast data from location is expired.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return 1 for expired or 0 for not expired.
     */
    @Query("SELECT CASE WHEN expirationTime < :now THEN 1 ELSE 0 END FROM OceanForecastEntity WHERE latitude = :lat AND longitude = :lon")
    fun oceanForecastExpired(lat: Double, lon: Double, now: Long = DateTime.now().millis) : Int

    /**
     * Checks if weather forecast data from location is expired.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return 1 for expired or 0 for not expired.
     */
    @Query("SELECT CASE WHEN expirationTime < :now THEN 1 ELSE 0 END FROM WeatherForecastEntity WHERE latitude = :lat AND longitude = :lon")
    fun weatherForecastExpired(lat: Double, lon: Double, now: Long = DateTime.now().millis) : Int

    /**
     * Get ocean forecast based on geographical location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @param timeAndDay local- time and day.
     * @return live data containing a list of ocean forecast entities.
     */
    @Query("SELECT * FROM OceanForecastEntity WHERE latitude = :lat AND longitude = :lon AND timeOfDay = :timeAndDay")
    fun getOceanForecast(lat: Double, lon: Double, timeAndDay: Long) : LiveData<OceanForecastEntity?>

    /**
     * Get weather forecast based on geographical location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @param timeAndDay local- time and day.
     * @return live data containing a list of weather forecast entities.
     */
    @Query("SELECT * FROM WeatherForecastEntity WHERE latitude = :lat AND longitude = :lon AND timeOfDay = :timeAndDay")
    fun getWeatherForecast(lat: Double, lon: Double, timeAndDay: Long) : LiveData<WeatherForecastEntity?>

    /**
     * Insert/update forecast favorite.
     * If an entity with equal primary key already exists, the existing entity is replaced.
     * @param favorite a favorite database entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertLocations(favorite: LocationEntity)

    /**
     * Insert/update ocean forecast data.
     * If an entity with equal primary key already exists, the existing entity is replaced.
     * @param forecast an ocean forecast entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertOceanForecast(forecast: OceanForecastEntity)

    /**
     * Insert/update weather forecast data.
     * If an entity with equal primary key already exists, the existing entity is replaced.
     * @param forecast a weather forecast entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertWeatherForecast(forecast: WeatherForecastEntity)

    /**
     * Delete forecast location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     */
    @Query("DELETE FROM LocationEntity WHERE latitude = :lat AND longitude = :lon")
    fun deleteLocation(lat: Double, lon: Double)

    /**
     * Delete ocean forecast.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     */
    @Query("DELETE FROM OceanForecastEntity WHERE latitude = :lat AND longitude = :lon")
    fun deleteOceanForecast(lat: Double, lon: Double)

    /**
     * Delete weather forecast.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     */
    @Query("DELETE FROM WeatherForecastEntity WHERE latitude = :lat AND longitude = :lon")
    fun deleteWeatherForecast(lat: Double, lon: Double)

    /**
     * Get available ocean forecast time and days.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return live data containing a list of available time and days as Long.
     */
    @Query("SELECT WFE.timeOfDay FROM WeatherForecastEntity AS WFE JOIN OceanForecastEntity AS OFE ON (WFE.timeOfDay = OFE.timeOfDay) WHERE WFE.latitude = :lat AND WFE.longitude = :lon AND OFE.latitude = :lat AND OFE.longitude = :lon ORDER BY WFE.timeOfDay")
    fun getTimeAndDays(lat: Double, lon: Double) : LiveData<List<Long>>
}