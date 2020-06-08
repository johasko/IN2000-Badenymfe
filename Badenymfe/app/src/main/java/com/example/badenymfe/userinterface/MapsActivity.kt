package com.example.badenymfe.userinterface

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.badenymfe.R
import com.example.badenymfe.databinding.ActivityMapsBinding
import com.example.badenymfe.viewmodel.ForecastViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber

// If map does not work in emulator, download google play services in SDK Manager.

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: ForecastViewModel by viewModels()

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMapsBinding
    private var marker: Marker? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_maps
        )
        binding.viewModel = viewModel

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Add location and navigate to MainActivity
        binding.buttonAddLocation.setOnClickListener {
            if (marker != null) {
                viewModel.insertLocation(marker!!.position.latitude, marker!!.position.longitude)
                Timber.d("Staring MainActivity")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Timber.e("Add location button was click, when it should be gone")
            }
        }

        /**
         * Displays back button
         * */
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Timber.d("Map ready")
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true

        getUserLocationPermission()

        map.setOnMapClickListener { location ->
            Timber.d("Map clicked")
            placeMarkerOnMap(location)
        }

        moveToUserLocation()
    }

    /**
     * Check if user location permission is granted, else request it.
     */
    private fun getUserLocationPermission() {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    /**
     * Move frame to user location.
     */
    private fun moveToUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // In some rare situations lastLocation can be null.
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12F))
            }
        }
    }

    /**
     * Place marker on map.
     * @param location where to place the marker.
     */
    private fun placeMarkerOnMap(location: LatLng) {
        marker?.remove()
        binding.buttonAddLocation.visibility = View.VISIBLE
        val markerOptions = MarkerOptions().apply {
            position(location)
            title(viewModel.getAddress(location))
            draggable(true)
        }
        marker = map.addMarker(markerOptions)
    }

    /**
     * Navigates back to LocationsFragment when back button is clicked
     * * */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
