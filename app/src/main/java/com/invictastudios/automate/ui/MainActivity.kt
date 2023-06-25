package com.invictastudios.automate.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.invictastudios.automate.R
import com.invictastudios.automate.databinding.ActivityMainBinding
import com.invictastudios.automate.utils.Utils
import com.invictastudios.automate.utils.isFragmentInBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        setListeners()

        binding.bottomNavBar.visibility = View.GONE
        binding.vehiclesButton.visibility = View.INVISIBLE
        binding.serviceStationsButton.visibility = View.INVISIBLE
        binding.accountButton.visibility = View.INVISIBLE
    }

    private fun setListeners() {
        binding.bottomBarVehicles.setOnClickListener {
            if (navController.isFragmentInBackStack(R.id.vehiclesScreenFragment)) {
                navController.popBackStack(R.id.vehiclesScreenFragment, false)
            } else {
                navController.navigate(
                    R.id.vehiclesScreenFragment, bundleOf(),
                    Utils.popUpDefaultNavigation()
                )
            }

        }

        binding.bottomBarServiceStations.setOnClickListener {
            if (navController.isFragmentInBackStack(R.id.serviceStationsScreenFragment)) {
                navController.popBackStack(R.id.serviceStationsScreenFragment, false)
            } else {
                navController.navigate(
                    R.id.serviceStationsScreenFragment, bundleOf(),
                    Utils.popUpDefaultNavigation()
                )
            }

        }

        binding.bottomBarAccount.setOnClickListener {
            if (navController.isFragmentInBackStack(R.id.accountScreenFragment)) {
                navController.popBackStack(R.id.accountScreenFragment, false)
            } else {
                navController.navigate(
                    R.id.accountScreenFragment, bundleOf(),
                    Utils.popUpDefaultNavigation()
                )
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}