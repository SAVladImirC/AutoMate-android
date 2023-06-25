package com.invictastudios.automate.ui.services_made_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.invictastudios.automate.R
import com.invictastudios.automate.adapters.ServicesMadeAdapter
import com.invictastudios.automate.adapters.VehicleOnClick
import com.invictastudios.automate.databinding.FragmentServicesMadeScreenBinding
import com.invictastudios.automate.utils.Constants
import com.invictastudios.automate.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServicesMadeScreenFragment : Fragment(), VehicleOnClick {

    private var _binding: FragmentServicesMadeScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesMadeViewModel by viewModels()
    private val servicesMadeAdapter = ServicesMadeAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesMadeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        arguments?.let {
            viewModel.getUserVehicles(it.getString(Constants.VEHICLE_ID).toString())
        }

        binding.servicesMadeList.adapter = servicesMadeAdapter
        binding.servicesMadeList.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.servicesMadeList.visibility = View.GONE
                binding.servicesMadeProgressbar.visibility = View.VISIBLE
            } else {
                binding.servicesMadeList.visibility = View.VISIBLE
                binding.servicesMadeProgressbar.visibility = View.GONE
            }
        }
        viewModel.vehicleInfoResponse.observe(viewLifecycleOwner) {
            it.data?.let { it1 ->
                it1.performedServices?.let { it2 ->
                    servicesMadeAdapter.updateList(
                        it2
                    )
                }
            }
        }
    }

    override fun onVehicleClick(id: String) {
        val bundle = bundleOf()
        bundle.putString(Constants.SERVICE_ID, id)
        findNavController().navigate( // navigating to home screen after successful login
            R.id.servicesMadeDetailScreenFragment,
            bundle,
            Utils.popUpLeftRightNavigation()
        )
    }
}