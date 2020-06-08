package com.example.badenymfe.userinterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.badenymfe.R
import com.example.badenymfe.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass for settings.
 * Currently not in use. Feature will be implemented in a later version.
 */
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
            inflater, R.layout.fragment_settings, container, false
        )
        return binding.root
    }
}
