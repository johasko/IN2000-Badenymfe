package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GmlTimePeriod(
    @Json(name = "gml:begin")
    val gmlBegin: String?,
    @Json(name = "gml:end")
    val gmlEnd: String?,
    @Json(name = "gml:id")
    val gmlId: String?
)