package com.example.badenymfe.userinterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.badenymfe.R
import com.example.badenymfe.databinding.FragmentForecastBinding
import com.example.badenymfe.viewmodel.ForecastViewModel
import timber.log.Timber

/**
 * Fragment for forecast section.
 */
class ForecastFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.weatherForecast.observe(viewLifecycleOwner, Observer {
            Timber.d("Weather forecast updated")
        })
        viewModel.oceanForecast.observe(viewLifecycleOwner, Observer {
            Timber.d("Ocean forecast updated")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentForecastBinding>(
            inflater, R.layout.fragment_forecast, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

}
