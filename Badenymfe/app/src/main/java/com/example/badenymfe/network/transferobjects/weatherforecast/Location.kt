package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "altitude")
    val altitude: String?,
    @Json(name = "cloudiness")
    val cloudiness: Cloudiness?,
    @Json(name = "dewpointTemperature")
    val dewpointTemperature: DewpointTemperature?,
    @Json(name = "fog")
    val fog: Fog?,
    @Json(name = "highClouds")
    val highClouds: HighClouds?,
    @Json(name = "humidity")
    val humidity: Humidity?,
    @Json(name = "latitude")
    // TODO - Does these need to be nullable?
    val latitude: String?,
    @Json(name = "longitude")
    val longitude: String?,
    @Json(name = "lowClouds")
    val lowClouds: LowClouds?,
    @Json(name = "maxTemperature")
    val maxTemperature: MaxTemperature?,
    @Json(name = "mediumClouds")
    val mediumClouds: MediumClouds?,
    @Json(name = "minTemperature")
    val minTemperature: MinTemperature?,
    @Json(name = "precipitation")
    val precipitation: Precipitation?,
    @Json(name = "pressure")
    val pressure: Pressure?,
    @Json(name = "symbol")
    val symbol: Symbol?,
    @Json(name = "symbolProbability")
    val symbolProbability: SymbolProbability?,
    @Json(name = "temperature")
    val temperature: Temperature?,
    @Json(name = "temperatureProbability")
    val temperatureProbability: TemperatureProbability?,
    @Json(name = "windDirection")
    val windDirection: WindDirection?,
    @Json(name = "windGust")
    val windGust: WindGust?,
    @Json(name = "windProbability")
    val windProbability: WindProbability?,
    @Json(name = "windSpeed")
    val windSpeed: WindSpeed?
)