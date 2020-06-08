package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediumClouds(
    @Json(name = "id")
    val id: String?,
    @Json(name = "percent")
    val percent: String?
)