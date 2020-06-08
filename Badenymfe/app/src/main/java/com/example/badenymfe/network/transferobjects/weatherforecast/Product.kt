package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "class")
    val classX: String?,
    @Json(name = "time")
    // TODO - Does this need to be nullable?
    val time: List<Time>?
)