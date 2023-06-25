package com.invictastudios.automate.ui.service_made_details_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.invictastudios.automate.adapters.ServicesPartAdapter
import com.invictastudios.automate.databinding.FragmentServicesMadeDetailScreenBinding
import com.invictastudios.automate.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ServicesMadeDetailScreenFragment : Fragment() {

    private var _binding: FragmentServicesMadeDetailScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ServicesMadeDetailViewModel by viewModels()
    private val servicesPartAdapter = ServicesPartAdapter(arrayListOf())
    private var invoiceId = "-1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesMadeDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        arguments?.let {
            viewModel.getServicesDetail(it.getString(Constants.SERVICE_ID).toString())
        }
        binding.servicesPartList.adapter = servicesPartAdapter
        binding.servicesPartList.layoutManager = LinearLayoutManager(context)
        binding.invoicesBt.setOnClickListener {
            val url = "http://192.168.100.34:8585/api/invoice/get-pdf/$invoiceId"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.scrollView.visibility = View.GONE
                binding.servicesMadeProgressbar.visibility = View.VISIBLE
            } else {
                binding.scrollView.visibility = View.VISIBLE
                binding.servicesMadeProgressbar.visibility = View.GONE
            }
        }
        viewModel.servicesInfoResponse.observe(viewLifecycleOwner) {
            it.data?.let { data ->
                data.replacements?.let { it1 -> servicesPartAdapter.updateList(it1) }
                binding.dateTv.append(data.performedOn)
                binding.mileageTv.append(data.mileage)
                binding.serviceStationTv.append(data.serviceStation?.name ?: "")
                binding.serviceTv.append(data.service?.name ?: "")
                invoiceId = data.serviceStation?.invoices?.get(0)?.id.toString()
            }
        }
    }
}