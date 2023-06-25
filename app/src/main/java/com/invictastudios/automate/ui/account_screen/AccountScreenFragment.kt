package com.invictastudios.automate.ui.account_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.invictastudios.automate.R
import com.invictastudios.automate.databinding.FragmentAccountScreenBinding
import com.invictastudios.automate.utils.Constants
import com.invictastudios.automate.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountScreenFragment : Fragment() {

    private var _binding: FragmentAccountScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountScreenBinding.inflate(inflater, container, false)
        updateViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setListeners()

    }

    private fun setListeners() {
        binding.settingsLogoutLayout.setOnClickListener {
            val sharedPrefs = activity?.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)!!
            with(sharedPrefs.edit()) {
                putString(Constants.USER_NAME, "")
                putString(Constants.USER_SURNAME, "")
                putInt(Constants.USER_ID, 0)
                apply()
            }
            val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            var backStack = navHostFragment.childFragmentManager.backStackEntryCount
            while (backStack >= 0) { // After a successful removal of the backpack navigation goes to home screen
                findNavController().popBackStack()
                backStack--
            }
            findNavController().navigate(
                R.id.logInScreenFragment, bundleOf(),
                Utils.popUpRightLeftNavigation()
            )
        }

    }

    private fun observeViewModel() {
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
        vehiclesButton.visibility = View.INVISIBLE
        serviceStationsButton.visibility = View.INVISIBLE
        accountButton.visibility = View.VISIBLE
        bottomBarVehiclesButton.isEnabled = true
        bottomBarServiceStationsButton.isEnabled = true
        bottomBarAccountButton.isEnabled = false
    }
}