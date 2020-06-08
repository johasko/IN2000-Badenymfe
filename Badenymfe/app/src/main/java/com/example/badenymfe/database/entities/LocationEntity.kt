package com.example.badenymfe.database.entities
import androidx.room.Entity
import com.example.badenymfe.domain.Location

/**
 * Defines a database entity for storing forecast favorites.
 */
@Entity(
    primaryKeys = ["latitude", "longitude"]
)
data class LocationEntity(
    // Geographical location.
    val latitude: Double,
    val longitude: Double,

    // User defined title.
    val title: String
)

/**
 * Convert list of location database entities to list of domain models for
 * access in user interface.
 */
fun List<LocationEntity>.asDomainModels(): List<Location> {
    return map { entity ->
        Location(
            title = entity.title,
            latitude = entity.latitude,
            longitude = entity.longitude
        )
    }
}
