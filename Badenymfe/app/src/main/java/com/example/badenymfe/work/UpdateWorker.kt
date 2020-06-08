package com.example.badenymfe.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.badenymfe.database.getForecastDatabase
import com.example.badenymfe.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Worker checks for expired forecast data once every hour.
 * Updates data set if needed.
 */
class UpdateWorker (context: Context, params: WorkerParameters) : Worker(context, params) {

    private val database = getForecastDatabase(context)

    override fun doWork(): Result {
        Timber.d("Starting to update forecasts")

        CoroutineScope(Dispatchers.IO).launch {
            val locations = database.forecastDao.getLocationsSynchronous()

            locations.forEach { location ->
                if (database.forecastDao.oceanForecastExpired(location.latitude, location.longitude) == 1) {
                    Timber.d("Ocean forecast data at location $location expired, deleting old data.")
                    database.forecastDao.deleteOceanForecast(location.latitude, location.longitude)

                    Timber.d("Fetching new ocean forecast data.")
                    val forecasts = Network.metApi.getOceanForecastAsync(location.latitude, location.longitude).await().asDatabaseEntities(location.latitude, location.longitude)
                    forecasts?.forEach { entity ->
                        database.forecastDao.upsertOceanForecast(entity)
                    }
                }

                if (database.forecastDao.weatherForecastExpired(location.latitude, location.longitude) == 1) {
                    Timber.d("Weather forecast data at location: $location expired, deleting old data.")
                    database.forecastDao.deleteWeatherForecast(location.latitude, location.longitude)

                    Timber.d("Fetching new weather forecast data.")
                    val forecasts = Network.metApi.getWeatherForecastAsync(location.latitude, location.longitude).await().asDatabaseEntities(location.latitude, location.longitude)
                    forecasts?.forEach { entity ->
                        database.forecastDao.upsertWeatherForecast(entity)
                    }
                }
            }
            Timber.d("Forecast update complete")
        }

        return Result.success()
    }
}