package com.invictastudios.automate.ui.vehicle_detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.invictastudios.automate.R
import com.invictastudios.automate.databinding.FragmentVehicleDetailScreenBinding
import com.invictastudios.automate.utils.Constants
import com.invictastudios.automate.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class VehicleDetailScreenFragment : Fragment() {

    private var _binding: FragmentVehicleDetailScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VehicleDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleDetailScreenBinding.inflate(inflater, container, false)
        updateViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.vehicleDetailScreenBackBt.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }

        arguments?.let {args ->
            viewModel.getUserVehicles(args.getString(Constants.VEHICLE_ID).toString())
            binding.servicesMadeBt.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(Constants.VEHICLE_ID,args.getString(Constants.VEHICLE_ID).toString())
                findNavController().navigate( // navigating to home screen after successful login
                    R.id.servicesMadeScreenFragment,
                    bundle,
                    Utils.popUpLeftRightNavigation()
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.vehicleDetailLayout.visibility = View.GONE
                binding.vehiclesProgressbar.visibility = View.VISIBLE
            } else {
                binding.vehicleDetailLayout.visibility = View.VISIBLE
                binding.vehiclesProgressbar.visibility = View.GONE
            }
        }
        viewModel.vehicleInfoResponse.observe(viewLifecycleOwner) {
            it.data?.let { data ->
                binding.registrationPlateTv.append(data.registrationPlate)
                binding.brandNameTv.append(data.model?.brand?.name ?: "")
                binding.modelNameTv.append(data.model?.name ?: "")
                binding.yearTv.append(data.model?.year.toString())
                binding.heightTv.append(data.height.toString())
                binding.weightTv.append(data.weight.toString())
                binding.mileageTv.append("${data.currentMileage} km")
                binding.nextServiceTv.append("${data.nextServiceMileage} km")
            }
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
        navBar.visibility = View.GONE
        vehiclesButton.visibility = View.INVISIBLE
        serviceStationsButton.visibility = View.INVISIBLE
        accountButton.visibility = View.INVISIBLE
        bottomBarVehiclesButton.isEnabled = false
        bottomBarServiceStationsButton.isEnabled = false
        bottomBarAccountButton.isEnabled = false
    }
}