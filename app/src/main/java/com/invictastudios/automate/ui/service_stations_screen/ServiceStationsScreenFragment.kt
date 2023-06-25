package com.invictastudios.automate.ui.service_stations_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.card.MaterialCardView
import com.invictastudios.automate.R
import com.invictastudios.automate.databinding.FragmentServiceStationsScreenBinding
import com.invictastudios.automate.databinding.FragmentVehiclesScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceStationsScreenFragment : Fragment() {

    private var _binding: FragmentServiceStationsScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServiceStationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceStationsScreenBinding.inflate(inflater, container, false)
        updateViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setListeners()

    }

    private fun setListeners() {


    }


    private fun observeViewModel() {

    }

    private fun updateViews() { // Bottom nav bar UI Logic
        val navBar: MaterialCardView = requireActivity().findViewById(R.id.bottom_nav_bar)
        val vehiclesButton: MaterialCardView = requireActivity().findViewById(R.id.vehicles_button)
        val serviceStationsButton: MaterialCardView = requireActivity().findViewById(R.id.service_stations_button)
        val accountButton: MaterialCardView = requireActivity().findViewById(R.id.account_button)
        val bottomBarVehiclesButton: ImageView = requireActivity().findViewById(R.id.bottom_bar_vehicles)
        val bottomBarServiceStationsButton: ImageView = requireActivity().findViewById(R.id.bottom_bar_service_stations)
        val bottomBarAccountButton: ImageView = requireActivity().findViewById(R.id.bottom_bar_account)
        navBar.visibility = View.VISIBLE
        vehiclesButton.visibility = View.INVISIBLE
        serviceStationsButton.visibility = View.VISIBLE
        accountButton.visibility = View.INVISIBLE
        bottomBarVehiclesButton.isEnabled = true
        bottomBarServiceStationsButton.isEnabled = false
        bottomBarAccountButton.isEnabled = true
    }
}