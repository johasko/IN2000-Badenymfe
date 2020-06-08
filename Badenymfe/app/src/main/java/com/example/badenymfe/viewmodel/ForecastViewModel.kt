package com.example.badenymfe.viewmodel

import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import com.example.badenymfe.database.getForecastDatabase
import com.example.badenymfe.repository.ForecastRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import timber.log.Timber
import java.io.IOException
import kotlin.math.round

/**
 * Provides data for user interface.
 */
class ForecastViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Coroutine job for view model.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * Coroutine scope for view model.
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Reference to forecast database.
     */
    private val database = getForecastDatabase(application)

    /**
     * Reference to forecast repository for data requests.
     */
    private val repository = ForecastRepository(database)

    /**
     * Reference to forecast locations for user interface access.
     */
    val locations = repository.locations

    /**
     * Reference to ocean forecast for user interface access.
     */
    var oceanForecast = repository.oceanForecast

    /**
     * Reference to weather forecast for user interface access.
     */
    var weatherForecast = repository.weatherForecast

    /**
     * Reference to time- and days for user interface access.
     */
    var timeAndDays = repository.timeAndDays

    /**
     * Insert forecast location.
     * Rounds location to three decimal places.
     * @param latitude geodetic latitude.
     * @param longitude geodetic longitude.
     */
    fun insertLocation(latitude: Double, longitude: Double) = viewModelScope.launch {
        val title = getAddress(LatLng(latitude, longitude))

        // Round latitude & longitude to three decimal places.
        val lat = round(latitude * 1000.0) / 1000.0
        val lon = round(longitude * 1000.0) / 1000.0

        repository.insertLocation(lat, lon, title)
    }

    /**
     * Delete forecast location.
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     */
    fun deleteLocation(lat: Double, lon: Double) = viewModelScope.launch {
        repository.deleteLocation(lat, lon)
    }

    /**
     * Called when the app process is killed.
     */
    override fun onCleared() {
        super.onCleared()
        // Stop any pending tasks.
        viewModelJob.cancel()
    }

    /**
     * Set latitude and longitude
     * @param lat geodetic latitude.
     * @param lon geodetic longitude.
     */
    fun setLocation(lat: Double, lon: Double) {
        repository.latitude = lat
        repository.longitude = lon
        timeAndDays = repository.timeAndDays
    }

    /**
     * Set local time and day
     * @param timeAndDay forecast time position.
     */
    fun setTimeAndDay(timeAndDay: DateTime) {
        repository.timeAndDay = timeAndDay
        weatherForecast = repository.weatherForecast
        oceanForecast = repository.oceanForecast
    }

    /**
     * Get address from location.
     * @param location geodetic latitude & longitude.
     */
    fun getAddress(location: LatLng): String {
        val geocoder = Geocoder(getApplication())
        val addresses: List<Address>?
        val address: Address?
        var addressText = "Valgt posisjon"

        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (null != addresses && addresses.isNotEmpty()) {
                address = addresses[0]
                addressText = address.getAddressLine(0)
            }
        } catch (e: IOException) {
            Timber.d(e.localizedMessage)
        }
        return addressText
    }
}