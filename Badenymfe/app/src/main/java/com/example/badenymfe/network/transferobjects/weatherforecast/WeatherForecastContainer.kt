package com.example.badenymfe.network.transferobjects.weatherforecast


import com.example.badenymfe.database.entities.WeatherForecastEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.joda.time.DateTime

/**
 * A container for weather forecast network response.
 */
@JsonClass(generateAdapter = true)
data class WeatherForecastContainer(
    @Json(name = "created")
    val created: String?,
    @Json(name = "meta")
    val meta: Meta?,
    @Json(name = "product")
    val product: Product?
) {
    /**
     * Convert network container in to a list of weather forecast database entities.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return list of weather forecast as database models.
     */
    fun asDatabaseEntities(lat: Double, lon: Double) : List<WeatherForecastEntity>? {
        val entities = mutableListOf<WeatherForecastEntity>()

        // There are no forecasts to convert.
        val forecasts = product?.time ?: return entities

        // Expiration time cannot be null.
        val expirationTime = meta?.model?.nextrun ?: return entities

        for (forecast in forecasts) {
            // Time of day is a primary key, therefore it cannot be null.
            val timeOfDay = forecast.from ?: continue

            // Only keep forecasts where from time equals to time.
            if (forecast.from != forecast.to) continue

            entities.add(
                WeatherForecastEntity(
                    latitude = lat,
                    longitude = lon,
                    timeOfDay = DateTime.parse(timeOfDay).millis,
                    expirationTime = DateTime.parse(expirationTime).millis,
                    highClouds = forecast.location?.highClouds?.percent?.toDoubleOrNull(),
                    mediumClouds = forecast.location?.mediumClouds?.percent?.toDoubleOrNull(),
                    lowClouds = forecast.location?.lowClouds?.percent?.toDoubleOrNull(),
                    cloudiness = forecast.location?.cloudiness?.percent?.toDoubleOrNull(),
                    airTemperature = forecast.location?.temperature?.value?.toDoubleOrNull(),
                    dewPointTemperature = forecast.location?.dewpointTemperature?.value?.toDoubleOrNull(),
                    atmosphericPressure = forecast.location?.pressure?.value?.toDoubleOrNull(),
                    windDirection = forecast.location?.windDirection?.deg?.toDoubleOrNull(),
                    windDescription = forecast.location?.windSpeed?.name.toString(),
                    windSpeed = forecast.location?.windSpeed?.mps?.toDoubleOrNull(),
                    windDirectionDesc = forecast.location?.windDirection?.name.toString(),
                    humidity = forecast.location?.humidity?.value?.toDoubleOrNull(),
                    fog = forecast.location?.fog?.percent?.toDoubleOrNull()
                )
            )
        }

        return entities
    }
}
