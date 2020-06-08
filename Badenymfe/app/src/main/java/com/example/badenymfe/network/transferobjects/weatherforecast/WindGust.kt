package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindGust(
    @Json(name = "id")
    val id: String?,
    @Json(name = "mps")
    val mps: String?
)