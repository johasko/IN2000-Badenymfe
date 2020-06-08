package com.example.badenymfe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.badenymfe.R
import com.example.badenymfe.databinding.LocationItemBinding
import com.example.badenymfe.domain.Location

/**
 * A bridge between the UI component and data set.
 */
class LocationAdapter(
    private val selectLocationCallback: LocationClick,
    private val deleteLocationCallback: LocationDelete
) : RecyclerView.Adapter<LocationViewHolder>() {

    var locations = listOf<Location>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val withDataBinding: LocationItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            LocationViewHolder.LAYOUT,
            parent,
            false
        )
        return LocationViewHolder(withDataBinding)
    }

    override fun getItemCount() = locations.count()

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.location = locations[position]
            it.selectLocationCallback = selectLocationCallback
            it.deleteLocationCallback = deleteLocationCallback
        }
    }
}

/**
 * Click listener for locations in the location recycler view.
 */
class LocationClick(val block: (Location) -> Unit) {
    /**
     * Called when a location is clicked in the location recycler view.
     */
    fun onClick(location: Location) = block(location)
}

/**
 * Click listener for delete location in the location recycler view.
 */
class LocationDelete(val block: (Location) -> Unit) {
    /**
     * Called when the delete button for a location is clicked in the location recycler view.
     */
    fun onDelete(location: Location) = block(location)
}

/**
 * View holder for location item.
 */
class LocationViewHolder(val viewDataBinding: LocationItemBinding):
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.location_item
    }
}