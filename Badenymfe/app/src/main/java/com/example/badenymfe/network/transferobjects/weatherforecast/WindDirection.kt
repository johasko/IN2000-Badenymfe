package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindDirection(
    @Json(name = "deg")
    val deg: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?
)