package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GmlTimeInstantX(
    @Json(name = "gml:id")
    val gmlId: String?,
    @Json(name = "gml:timePosition")
    val gmlTimePosition: String?
)