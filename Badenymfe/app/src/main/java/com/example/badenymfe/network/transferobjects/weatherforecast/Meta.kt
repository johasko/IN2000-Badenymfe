package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "model")
    val model: Model?
)