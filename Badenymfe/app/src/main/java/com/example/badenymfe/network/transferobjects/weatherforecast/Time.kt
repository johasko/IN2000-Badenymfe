package com.example.badenymfe.network.transferobjects.weatherforecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Time(
    @Json(name = "datatype")
    val datatype: String?,
    @Json(name = "from")
    // TODO - Does this need to be nullable?
    val from: String?,
    @Json(name = "location")
    val location: Location?,
    @Json(name = "to")
    val to: String?
)