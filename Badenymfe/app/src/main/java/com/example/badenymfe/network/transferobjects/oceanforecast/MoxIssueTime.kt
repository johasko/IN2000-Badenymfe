package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoxIssueTime(
    @Json(name = "gml:TimeInstant")
    val gmlTimeInstant: GmlTimeInstant?
)