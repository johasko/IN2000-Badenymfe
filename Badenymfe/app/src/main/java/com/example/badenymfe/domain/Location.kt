package com.example.badenymfe.domain

/**
 * Defines a location as a domain model for direct use in
 * the graphical user interface.
 */
data class Location(
    // Location title defined by GMaps.
    val title: String,

    // Geographical location.
    val latitude: Double,
    val longitude: Double
) {
    val locationString: String
    get() = "lat $latitude, lon $longitude"
}