package com.example.badenymfe.domain

import org.joda.time.DateTime

/**
 * Defines an ocean forecast as a domain model for direct use in
 * the graphical user interface.
 */
data class OceanForecast(
    // The time for when data applies.
    val timeAndDay: DateTime,

    // Ocean forecast data.
    val seaTemperature: Double?,
    val currentDirection: Double?,
    val currentSpeed: Double?,
    val waveDirection: Double?,
    val waveHeight: Double?,
    val seaDepth: Double?,
    val icePresence: Int?
) {
    val seaTemperatureText: String
        get() = "      Havtemperatur: ${seaTemperature ?: "?"} °C"
    val currentDirectionText: String
        get() = "Current direction: ${currentDirection ?: "?"} °"
    val currentSpeedText: String
        get() = "Current speed: ${currentSpeed ?: "?"} m/s"
    val waveDirectionText: String
        get() = "Wave direction: ${waveDirection ?: "?"} °"
    val waveHeightText: String
        get() = "    Bølgehøyde: ${waveHeight ?: "?"} m"
    val seaDepthText: String
        get() = "    Havdybde: ${seaDepth ?: "?"} m"
    val icePresenceText: String
        get() = "Ice presence: ${icePresence ?: "?"} %"
    val jellyfishProb : String
        get() = "    Risiko for brennmaneter: ${jellyfishOdds(seaTemperature)}"


    /**
     * Calculates the odds of encountering a jelly fish.
     * Based on temperature.
     */
    private fun jellyfishOdds(temp : Double?): String{
        if (temp != null) {
            if(temp < 10 || temp > 26) {return "Lav"}
            else if (temp > 15 && temp < 21){return "Høy"}
            else{return "Medium"}
        }
        return "Ingen data tilgjengelig"
    }


}

