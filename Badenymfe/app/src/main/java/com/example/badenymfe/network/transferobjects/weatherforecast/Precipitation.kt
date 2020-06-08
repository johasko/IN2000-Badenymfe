package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Precipitation(
    @Json(name = "maxvalue")
    val maxvalue: String?,
    @Json(name = "minvalue")
    val minvalue: String?,
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "value")
    val value: String?
)