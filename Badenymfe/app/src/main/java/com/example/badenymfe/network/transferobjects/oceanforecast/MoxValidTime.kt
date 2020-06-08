package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoxValidTime(
    @Json(name = "gml:TimePeriod")
    val gmlTimePeriod: GmlTimePeriod?
)