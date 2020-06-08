package com.example.badenymfe.network.transferobjects.oceanforecast


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetnoOceanForecast(
    @Json(name = "gml:id")
    val gmlId: String?,
    @Json(name = "mox:meanTotalWaveDirection")
    val moxMeanTotalWaveDirection: MoxMeanTotalWaveDirection?,
    @Json(name = "mox:seaBottomTopography")
    val moxSeaBottomTopography: MoxSeaBottomTopography?,
    @Json(name = "mox:seaCurrentDirection")
    val moxSeaCurrentDirection: MoxSeaCurrentDirection?,
    @Json(name = "mox:seaCurrentSpeed")
    val moxSeaCurrentSpeed: MoxSeaCurrentSpeed?,
    @Json(name = "mox:seaIcePresence")
    val moxSeaIcePresence: MoxSeaIcePresence?,
    @Json(name = "mox:seaTemperature")
    val moxSeaTemperature: MoxSeaTemperature?,
    @Json(name = "mox:significantTotalWaveHeight")
    val moxSignificantTotalWaveHeight: MoxSignificantTotalWaveHeight?,
    @Json(name = "mox:validTime")
    val moxValidTime: MoxValidTime?
)