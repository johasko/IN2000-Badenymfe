package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoxObservedProperty(
    @Json(name = "xlink:href")
    val xlinkHref: String?
)