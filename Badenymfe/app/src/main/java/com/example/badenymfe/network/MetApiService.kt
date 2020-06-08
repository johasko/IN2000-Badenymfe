package com.example.badenymfe.network

import com.example.badenymfe.network.transferobjects.weatherforecast.WeatherForecastContainer
import com.example.badenymfe.network.transferobjects.oceanforecast.OceanForecastContainer
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A Retrofit service to fetch forecast data from the Met-API.
 */
interface MetApiService {

    /**
     * Fetch ocean forecast from given geographical location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return a deferred ocean forecast transfer object container.
     */
    @GET("weatherapi/oceanforecast/0.9/.json")
    fun getOceanForecastAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ) : Deferred<OceanForecastContainer>

    /**
     * Fetch weather forecast from giver geographical location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     * @return a deferred weather forecast transfer object container.
     */
    @GET("weatherapi/locationforecast/1.9/.json")
    fun getWeatherForecastAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ) : Deferred<WeatherForecastContainer>
}