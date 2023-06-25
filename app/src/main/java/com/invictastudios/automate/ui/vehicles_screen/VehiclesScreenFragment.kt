package com.invictastudios.automate.ui.vehicles_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.invictastudios.automate.R
import com.invictastudios.automate.adapters.VehicleOnClick
import com.invictastudios.automate.adapters.VehiclesAdapter
import com.invictastudios.automate.databinding.FragmentVehiclesScreenBinding
import com.invictastudios.automate.utils.Constants
import com.invictastudios.automate.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehiclesScreenFragment : Fragment(), VehicleOnClick {

    private var _binding: FragmentVehiclesScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VehiclesViewModel by viewModels()
    private val vehiclesAdapter = VehiclesAdapter(arrayListOf(),this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehiclesScreenBinding.inflate(inflater, container, false)
        updateViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setListeners()
        val sharedPrefs =
            activity?.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)!!
        val userId = sharedPrefs.getInt(Constants.USER_ID, -1)
        userId.let {
            viewModel.getUserVehicles(userId.toString())
        }

        binding.vehiclesList.adapter = vehiclesAdapter
        binding.vehiclesList.layoutManager = LinearLayoutManager(context)
    }

    private fun setListeners() {


    }


    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner){
            if(it){
                binding.vehiclesList.visibility = View.GONE
                binding.vehiclesProgressbar.visibility = View.VISIBLE
            } else {
                binding.vehiclesList.visibility = View.VISIBLE
                binding.vehiclesProgressbar.visibility = View.GONE
            }
        }
        viewModel.userVehiclesResponse.observe(viewLifecycleOwner) {
            it.data?.let { it1 -> vehiclesAdapter.updateList(it1) }
        }
    }

    private fun updateViews() { // Bottom nav bar UI Logic
        val navBar: MaterialCardView = requireActivity().findViewById(R.id.bottom_nav_bar)
        val vehiclesButton: MaterialCardView = requireActivity().findViewById(R.id.vehicles_button)
        val serviceStationsButton: MaterialCardView =
            requireActivity().findViewById(R.id.service_stations_button)
        val accountButton: MaterialCardView = requireActivity().findViewById(R.id.account_button)
        val bottomBarVehiclesButton: ImageView =
            requireActivity().findViewById(R.id.bottom_bar_vehicles)
        val bottomBarServiceStationsButton: ImageView =
            requireActivity().findViewById(R.id.bottom_bar_service_stations)
        val bottomBarAccountButton: ImageView =
            requireActivity().findViewById(R.id.bottom_bar_account)
        navBar.visibility = View.VISIBLE
        vehiclesButton.visibility = View.VISIBLE
        serviceStationsButton.visibility = View.INVISIBLE
        accountButton.visibility = View.INVISIBLE
        bottomBarVehiclesButton.isEnabled = false
        bottomBarServiceStationsButton.isEnabled = true
        bottomBarAccountButton.isEnabled = true
    }

    override fun onVehicleClick(id: String) {
        val bundle = bundleOf()
        bundle.putString(Constants.VEHICLE_ID,id)
        findNavController().navigate( // navigating to home screen after successful login
            R.id.vehicleDetailScreenFragment,
            bundle,
            Utils.popUpLeftRightNavigation()
        )
    }
}