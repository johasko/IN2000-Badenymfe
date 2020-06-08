package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoxForecast(
    @Json(name = "metno:OceanForecast")
    val metnoOceanForecast: MetnoOceanForecast?
)