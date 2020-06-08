package com.example.badenymfe

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.badenymfe.work.UpdateWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Override application on order to initialize Timber logging.
 */
class BadenymfeApplication: Application() {

    private lateinit var work: PeriodicWorkRequest
    private val uniqueWorkName = "Update forecast data"

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("Logger initialized.")
        // Uncomment to delete database on app launch.
        //deleteDatabase("ForecastDatabase")

        Timber.d("Enqueuing periodic worker.")
        work = PeriodicWorkRequestBuilder<UpdateWorker>(1, TimeUnit.HOURS).build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            work)
    }

    override fun onTerminate() {
        super.onTerminate()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.cancelAllWorkByTag(uniqueWorkName)
    }
}