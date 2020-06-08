package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GmlPoint(
    @Json(name = "gml:id")
    val gmlId: String?,
    @Json(name = "gml:pos")
    val gmlPos: String?,
    @Json(name = "srsName")
    val srsName: String?
)