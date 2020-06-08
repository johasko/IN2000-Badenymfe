package com.example.badenymfe.network.transferobjects.oceanforecast


import com.example.badenymfe.database.entities.OceanForecastEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.joda.time.DateTime

/**
 * A container for ocean forecast network response.
 */
@JsonClass(generateAdapter = true)
data class OceanForecastContainer(
    @Json(name = "gml:description")
    val gmlDescription: String?,
    @Json(name = "gml:id")
    val gmlId: String?,
    @Json(name = "mox:forecast")
    val moxForecast: List<MoxForecast>?,
    @Json(name = "mox:forecastPoint")
    val moxForecastPoint: MoxForecastPoint?,
    @Json(name = "mox:issueTime")
    val moxIssueTime: MoxIssueTime?,
    @Json(name = "mox:nextIssueTime")
    val moxNextIssueTime: MoxNextIssueTime?,
    @Json(name = "mox:observedProperty")
    val moxObservedProperty: MoxObservedProperty?,
    @Json(name = "mox:procedure")
    val moxProcedure: MoxProcedure?,
    @Json(name = "xmlns:gml")
    val xmlnsGml: String?,
    @Json(name = "xmlns:metno")
    val xmlnsMetno: String?,
    @Json(name = "xmlns:mox")
    val xmlnsMox: String?,
    @Json(name = "xmlns:xlink")
    val xmlnsXlink: String?,
    @Json(name = "xsi:schemaLocation")
    val xsiSchemaLocation: String?
) {
    /**
     * Convert network container in to a list of ocean forecast database entities.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return list of ocean forecast as database models.
     */
    fun asDatabaseEntities(lat: Double, lon: Double) : List<OceanForecastEntity>? {
        val entities = mutableListOf<OceanForecastEntity>()

        // There are no forecasts to convert.
        val forecasts = moxForecast ?: return entities

        // Expiration time cannot be null.
        val expirationTime = moxNextIssueTime?.gmlTimeInstant?.gmlTimePosition ?: return entities

        for (forecast in forecasts) {
            // Time of day is a primary key, therefore it cannot be null.
            val timeOfDay = forecast.metnoOceanForecast?.moxValidTime?.gmlTimePeriod?.gmlBegin ?: continue

            entities.add(
                OceanForecastEntity(
                    latitude = lat,
                    longitude = lon,
                    timeOfDay = DateTime.parse(timeOfDay).millis,
                    expirationTime = DateTime.parse(expirationTime).millis,
                    seaTemperature = forecast.metnoOceanForecast.moxSeaTemperature?.content?.toDoubleOrNull(),
                    currentDirection = forecast.metnoOceanForecast.moxSeaCurrentDirection?.content?.toDoubleOrNull(),
                    currentSpeed = forecast.metnoOceanForecast.moxSeaCurrentSpeed?.content?.toDoubleOrNull(),
                    waveDirection = forecast.metnoOceanForecast.moxMeanTotalWaveDirection?.content?.toDoubleOrNull(),
                    waveHeight = forecast.metnoOceanForecast.moxSignificantTotalWaveHeight?.content?.toDoubleOrNull(),
                    seaDepth = forecast.metnoOceanForecast.moxSeaBottomTopography?.content?.toDoubleOrNull(),
                    icePresence = forecast.metnoOceanForecast.moxSeaIcePresence?.content?.toIntOrNull()
                )
            )
        }
        return entities
    }
}