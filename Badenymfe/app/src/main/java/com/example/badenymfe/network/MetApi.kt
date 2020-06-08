package com.example.badenymfe.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Our main entry point for network access towards the MetAPI.
 * Utilizes Moshi as JSON parser & Retrofit as HTTP client.
 * A proxy server is used in order to relieve Meteorologisk institutt
 * from excessive get-requests.
 */
object Network {
    // Build Moshi JSON-parser.
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Build retrofit HTTP client.
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://in2000-apiproxy.ifi.uio.no")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val metApi: MetApiService = retrofit.create(MetApiService::class.java)
}