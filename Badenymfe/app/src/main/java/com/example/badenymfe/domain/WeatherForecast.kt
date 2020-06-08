package com.example.badenymfe.domain

import org.joda.time.DateTime

/**
 * Defines an weather forecast as a domain model for direct use in
 * the graphical user interface.
 */
data class WeatherForecast(
    // The time for when data applies.
    val timeAndDay: DateTime,

    // Weather forecast data.
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
    val highCloudsText: String
        get() = "High clouds: ${highClouds ?: "?"} %"
    val mediumCloudsText: String
        get() = "Medium clouds: ${mediumClouds ?: "?"} %"
    val lowCloudsText: String
        get() = "Low clouds: ${lowClouds ?: "?"} %"
    val cloudinessText: String
        get() = "Cloudiness: ${cloudiness ?: "?"} %"
    val airTemperatureText: String
        get() = "      Temperatur: ${airTemperature ?: "?"} °C"
    val dewPointTemperatureText: String
        get() = "Dew point temperature: ${dewPointTemperature ?: "?"} °C"
    val atmosphericPressureText: String
        get() = "Atmospheric pressure: ${atmosphericPressure ?: "?"} Pa"
    val windDirectionText: String
        get() = "Wind direction: ${windDirection ?: "?"} ° - ${windDirectionDesc ?: "?"}"
    val windSpeedText: String
        get() = "    Vindhastighet: ${windSpeed ?: "?"} m/s"
    val windDescriptionText: String
        get() = "    ${windDescription ?: "?"}"
    val humidityText: String
        get() = "Humidity: ${humidity ?: "?"}"
    val fogText: String
        get() = "Fog: ${fog ?: "?"}"

}