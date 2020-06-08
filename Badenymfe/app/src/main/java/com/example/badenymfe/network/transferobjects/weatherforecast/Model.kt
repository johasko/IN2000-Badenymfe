package com.example.badenymfe.network.transferobjects.weatherforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Model(
    @Json(name = "from")
    val from: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nextrun")
    val nextrun: String?,
    @Json(name = "runended")
    val runended: String?,
    @Json(name = "termin")
    val termin: String?,
    @Json(name = "to")
    val to: String?
)