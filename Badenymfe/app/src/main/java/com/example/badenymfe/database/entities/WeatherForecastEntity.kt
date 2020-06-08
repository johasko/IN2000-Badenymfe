package com.example.badenymfe.database.entities
import androidx.room.Entity
import com.example.badenymfe.domain.WeatherForecast
import org.joda.time.DateTime

/**
 * Defines a database entity for storing weather forecasts.
 */
@Entity(
    primaryKeys = ["latitude", "longitude", "timeOfDay"]
)
data class WeatherForecastEntity(
    // Geographical position.
    val latitude: Double,
    val longitude: Double,

    // For when data applies.
    val timeOfDay: Long,

    // Forecast data.
    val expirationTime: Long,
    val highClouds: Double?,
    val mediumClouds: Double?,
    val lowClouds: Double?,
    val cloudiness: Double?,
    val airTemperature: Double?,
    val dewPointTemperature: Double?,
    val atmosphericPressure: Double?,
    val windDirection: Double?,
    val windDirectionDesc: String?,
    val windSpeed: Double?,
    val windDescription: String?,
    val humidity: Double?,
    val fog: Double?
) {
    /**
     * Convert list of weather forecast database entities to list of domain models for
     * access in user interface.
     */
    fun asDomainModel() : WeatherForecast {
        return WeatherForecast(
            timeAndDay = DateTime(timeOfDay),
            highClouds = highClouds,
            mediumClouds = mediumClouds,
            lowClouds = lowClouds,
            cloudiness = cloudiness,
            airTemperature = airTemperature,
            dewPointTemperature = dewPointTemperature,
            atmosphericPressure = atmosphericPressure,
            windDirection = windDirection,
            windDirectionDesc = windDirectionDesc,
            windSpeed = windSpeed,
            windDescription = windDescription,
            humidity = humidity,
            fog = fog
        )
    }
}
