package com.example.badenymfe.userinterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.badenymfe.R
import com.example.badenymfe.adapter.HourAdapter
import com.example.badenymfe.adapter.HourClick
import com.example.badenymfe.databinding.FragmentHourByHourBinding
import com.example.badenymfe.viewmodel.ForecastViewModel
import timber.log.Timber

/**
 * A simple [Fragment] where the user can select a forecast time slot.
 */
class HourByHourFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()
    private var viewModelAdapter: HourAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.timeAndDays.observe(viewLifecycleOwner, Observer { timeAndDay ->
            viewModelAdapter?.timeAndDays = timeAndDay

            // Display data not found dialog.
            if(timeAndDay.size == 0){
                Timber.d("Data not found.")
                view?.findViewById<RelativeLayout>(R.id.talking_jelly_hour)?.visibility = VISIBLE
            }else{
                view?.findViewById<RelativeLayout>(R.id.talking_jelly_hour)?.visibility = GONE
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHourByHourBinding>(
            inflater, R.layout.fragment_hour_by_hour, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // Called when user taps hour item.
        viewModelAdapter = HourAdapter(HourClick { timeAndDay ->
            Timber.d("Setting time and day: $timeAndDay")
            viewModel.setTimeAndDay(timeAndDay)

            // Navigate to forecast fragment.
            val action = HourByHourFragmentDirections.actionHourByHourFragmentToForecastFragment()
            view?.findNavController()?.navigate(action)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }



}
