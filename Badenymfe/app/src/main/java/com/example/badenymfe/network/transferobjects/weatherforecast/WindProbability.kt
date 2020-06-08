package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindProbability(
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "value")
    val value: String?
)