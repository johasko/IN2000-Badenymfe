package com.example.badenymfe.userinterface

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.badenymfe.R
import com.example.badenymfe.adapter.LocationAdapter
import com.example.badenymfe.adapter.LocationClick
import com.example.badenymfe.adapter.LocationDelete
import com.example.badenymfe.databinding.FragmentLocationsBinding
import com.example.badenymfe.domain.Location
import com.example.badenymfe.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_locations.*
import timber.log.Timber

/**
 * A simple [Fragment] for displaying saved locations.
 * Fragment allows you to select locations or delete them.
 */
class LocationsFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()
    private var viewModelAdapter: LocationAdapter? = null
    private lateinit var dialogClickListener: DialogInterface.OnClickListener
    private lateinit var deleteLocation: Location

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            locations.apply {
                viewModelAdapter?.locations = locations
            }
            // Display data not found dialog.
            if(locations.size == 0){
                Timber.d("Data not found.")
                view?.findViewById<RelativeLayout>(R.id.talking_jelly)?.visibility = View.VISIBLE
            }else{
                view?.findViewById<RelativeLayout>(R.id.talking_jelly)?.visibility = View.GONE
            }
        })

        dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    Timber.d("User selected 'yes'")
                    Toast.makeText(
                        view?.context,
                        "Lokasjon slettet",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.deleteLocation(
                        deleteLocation.latitude,
                        deleteLocation.longitude
                    )
                }
                else -> {
                    Timber.d("User selected 'no'")
                    Toast.makeText(
                        view?.context,
                        "Sletting avbrutt",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        button_add_location_from_favs.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLocationsBinding>(
            inflater, R.layout.fragment_locations, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModelAdapter = LocationAdapter(
            LocationClick { location ->
                Timber.d("Selecting location lat: ${location.latitude}, lon: ${location.longitude}")
                viewModel.setLocation(location.latitude, location.longitude)
                val action = LocationsFragmentDirections.actionLocationsFragmentToHourByHourFragment()
                view?.findNavController()?.navigate(action)
            },
            LocationDelete { location ->
                Timber.d("Deleting location lat: ${location.longitude}, lon: ${location.longitude}")
                deleteLocation = location
                val alertDialog : AlertDialog = AlertDialog.Builder(view?.context).apply {
                    setMessage("Er du sikker på at du vil slette denne lokasjonen?")
                    setPositiveButton("Yeah buddy!", dialogClickListener)
                    setNegativeButton("Hell no!", dialogClickListener)
                }.show()

                //getting positivebutton and changing textcolor to red for design purposes
                val positiveButton: Button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setTextColor(resources.getColor(R.color.colorRed))
            }
        )

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root


    }

}
