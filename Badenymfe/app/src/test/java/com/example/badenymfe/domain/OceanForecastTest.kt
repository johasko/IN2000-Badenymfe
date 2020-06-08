package com.example.badenymfe.domain

import junit.framework.Assert.assertEquals
import org.joda.time.DateTime
import org.junit.Test


class OceanForecastTest {

    @Test
    fun getJellyfishProb() {
        val oceanForecast = OceanForecast(
            timeAndDay = DateTime(),
            seaTemperature = 1.0,
            currentDirection = 1.0,
            currentSpeed = 1.0,
            waveDirection = 1.0,
            waveHeight = 1.0,
            seaDepth = 32.0,
            icePresence = 0
        )
        assertEquals("    Risiko for brennmaneter: Lav", oceanForecast.jellyfishProb)

    }

    @Test
    fun getJellyfishProbMedium() {
        val oceanForecast = OceanForecast(
            timeAndDay = DateTime(),
            seaTemperature = 11.0,
            currentDirection = 1.0,
            currentSpeed = 1.0,
            waveDirection = 1.0,
            waveHeight = 1.0,
            seaDepth = 32.0,
            icePresence = 0
        )
        assertEquals("    Risiko for brennmaneter: Medium", oceanForecast.jellyfishProb)

    }

    @Test
    fun getJellyfishProbHigh() {
        val oceanForecast = OceanForecast(
            timeAndDay = DateTime(),
            seaTemperature = 20.0,
            currentDirection = 1.0,
            currentSpeed = 1.0,
            waveDirection = 1.0,
            waveHeight = 1.0,
            seaDepth = 32.0,
            icePresence = 0
        )
        assertEquals("    Risiko for brennmaneter: Høy", oceanForecast.jellyfishProb)

    }

    @Test
    fun getJellyfishProbNull() {
        val oceanForecast = OceanForecast(
            timeAndDay = DateTime(),
            seaTemperature = null,
            currentDirection = 1.0,
            currentSpeed = 1.0,
            waveDirection = 1.0,
            waveHeight = 1.0,
            seaDepth = 32.0,
            icePresence = 0
        )
        assertEquals("    Risiko for brennmaneter: Ingen data tilgjengelig", oceanForecast.jellyfishProb)

    }
}