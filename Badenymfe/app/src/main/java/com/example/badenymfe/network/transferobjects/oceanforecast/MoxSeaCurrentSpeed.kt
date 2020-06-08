package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoxSeaCurrentSpeed(
    @Json(name = "content")
    val content: String?,
    @Json(name = "uom")
    val uom: String?
)