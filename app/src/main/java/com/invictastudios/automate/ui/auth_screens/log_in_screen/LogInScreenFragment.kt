package com.invictastudios.automate.ui.auth_screens.log_in_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.invictastudios.automate.R
import com.invictastudios.automate.databinding.FragmentLogInScreenBinding
import com.invictastudios.automate.utils.Constants
import com.invictastudios.automate.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInScreenFragment : Fragment() {

    private var _binding: FragmentLogInScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LogInScreenViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPreferences
    private var emailFormatCorrect = false
    private var loginPressed = false // login button pressed flag
    private var email = ""
    private var emailFilled = false
    private var passwordFilled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        updateViews()

        sharedPrefs = activity?.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)!!
        val userName = sharedPrefs.getString(Constants.USER_NAME, "")
        if(!userName.isNullOrEmpty()){
            findNavController().popBackStack()
            findNavController().navigate( // navigating to home screen after successful login
                R.id.vehiclesScreenFragment,
                bundleOf(),
                Utils.popUpLeftRightNavigation()
            )
        }
        val rememberedEmail = sharedPrefs.getString(Constants.REMEMBER_LOGIN_EMAIL, "")
        if (!rememberedEmail.equals("")) {
            emailFormatCorrect = true
            emailFilled = true
        }
        binding.logInEmailCard.editText?.setText(rememberedEmail) // getting remembered email in case there is one
        binding.logInBt.isEnabled = emailFilled && passwordFilled && emailFormatCorrect
        binding.logInEmailCard.editText?.text?.let { emailFilled = it.isNotEmpty() }
        setListeners()
    }

    private fun setListeners() {
        binding.logInEmailCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let { emailFilled = it.isNotEmpty() }
            binding.logInEmailCard.apply {
                val correctInput = Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()
                if (!correctInput) {
                    isErrorEnabled = true
                    emailFormatCorrect = false
                    error = context.getString(R.string.email_format_incorrect)
                    endIconMode = TextInputLayout.END_ICON_CUSTOM
                    endIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_error)
                    boxStrokeErrorColor
                } else {
                    emailFormatCorrect = true
                    isErrorEnabled = false
                    endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
            binding.logInBt.isEnabled = emailFilled && passwordFilled && emailFormatCorrect
        }

        binding.logInPasswordCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let { passwordFilled = it.isNotEmpty() }
            binding.logInBt.isEnabled = emailFilled && passwordFilled && emailFormatCorrect
        }

        binding.logInCreateAccountTv.setOnClickListener { // Navigation to Sign up screen
            findNavController().navigate(
                R.id.signUpScreenFragment,
                bundleOf(),
                Utils.popUpLeftRightNavigation()
            )
        }

        binding.logInBt.setOnClickListener {
            email = binding.logInEmailCard.editText?.text.toString()
            val password = binding.logInPasswordCard.editText?.text.toString()
            viewModel.login(email, password)
            loginPressed = true
        }

    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) { loading -> // Loading observable for the progress bar
            if (loading)
                binding.logInProgressbar.visibility = View.VISIBLE
            else
                binding.logInProgressbar.visibility = View.GONE
        }

        viewModel.loginResponse.observe(viewLifecycleOwner) {loginResponse ->
            val name = loginResponse.data?.name
            name?.let {
                if(name.isNotEmpty()) {
                    findNavController().popBackStack()
                    findNavController().navigate( // navigating to home screen after successful login
                        R.id.vehiclesScreenFragment,
                        bundleOf(),
                        Utils.popUpLeftRightNavigation()
                    )
                    with(sharedPrefs.edit()) {
                        putString(Constants.USER_NAME, loginResponse.data?.name)
                        putString(Constants.USER_SURNAME, loginResponse.data?.surname)
                        putString(Constants.USER_USERNAME, loginResponse.data?.username)
                        putString(Constants.USER_EMAIl, loginResponse.data?.email)
                        loginResponse.data?.id?.let { putInt(Constants.USER_ID, it) }
                        apply()
                    }
                    rememberEmail()
                }
            }
        }

    }

    private fun rememberEmail() {
        val rememberMeChecked = binding.logInRememberMeCb.isChecked
        if (rememberMeChecked) {
            with(sharedPrefs.edit()) {
                putString(Constants.REMEMBER_LOGIN_EMAIL, email)
                apply()
            }
        } else {
            with(sharedPrefs.edit()) {
                putString(Constants.REMEMBER_LOGIN_EMAIL, "")
                apply()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}