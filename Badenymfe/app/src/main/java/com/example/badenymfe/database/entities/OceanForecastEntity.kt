package com.example.badenymfe.database.entities
import androidx.room.Entity
import com.example.badenymfe.domain.OceanForecast
import org.joda.time.DateTime

/**
 * Defines a database entity for storing ocean forecasts.
 */
@Entity(
    primaryKeys = ["latitude", "longitude", "timeOfDay"]
)
data class OceanForecastEntity (
    // Geographical position.
    val latitude: Double,
    val longitude: Double,

    // For when data applies.
    val timeOfDay: Long,

    // Forecast data
    val expirationTime: Long,
    val seaTemperature: Double?,
    val currentDirection: Double?,
    val currentSpeed: Double?,
    val waveDirection: Double?,
    val waveHeight: Double?,
    val seaDepth: Double?,
    val icePresence: Int?
) {
    /**
     * Convert list of ocean forecast database entities to list of domain models for
     * access in user interface.
     */
    fun asDomainModel() : OceanForecast {
        return OceanForecast(
            timeAndDay = DateTime(timeOfDay),
            seaTemperature = seaTemperature,
            currentDirection = currentDirection,
            currentSpeed = currentSpeed,
            waveDirection = waveDirection,
            waveHeight = waveHeight,
            seaDepth = seaDepth,
            icePresence = icePresence
        )
    }
}
