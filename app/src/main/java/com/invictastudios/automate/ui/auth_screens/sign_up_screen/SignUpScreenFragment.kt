package com.invictastudios.automate.ui.auth_screens.sign_up_screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.invictastudios.automate.R
import com.invictastudios.automate.databinding.FragmentSignUpScreenBinding
import com.invictastudios.automate.utils.Constants
import com.invictastudios.automate.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpScreenFragment : Fragment() {

    private var _binding: FragmentSignUpScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpScreenViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPreferences
    private var signUpPressed = false
    private var emailFormatCorrect = false
    private var emailFilled = false
    private var passwordFormatCorrect = false
    private var passwordFilled = false
    private var passwordNotShort = false
    private var passwordNotLong = false
    private var confirmedPasswordMatch = false
    private var confirmPasswordFilled = false
    private var password = ""
    private var conditionsChecked = false
    private var nameFilled = false
    private var nameNotLong = true
    private var lastNameFilled = false
    private var lastNameNotLong = true
    private var usernameFilled = false
    private var usernameNotLong = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        sharedPrefs = activity?.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)!!
        binding.signUpContinueEmailBt.isEnabled =
            emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                    && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                    && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                    && usernameNotLong
        setListeners()
    }

    private fun setListeners() {
        binding.signUpBackArrowIv.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }

        binding.signUpEmailCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let { emailFilled = it.isNotEmpty() }
            binding.signUpEmailCard.apply {
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
            binding.signUpContinueEmailBt.isEnabled =
                emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                        && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                        && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                        && usernameNotLong
        }

        binding.signUpPasswordCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let {
                passwordFilled = it.isNotEmpty()
                password = it.toString()
                binding.signUpPasswordCard.apply {
                    val noWhiteSpace = !it.startsWith(" ") && !it.endsWith(" ")
                    val correctFormat = checkPassword(it.toString())

                    if (!noWhiteSpace) {
                        isErrorEnabled = true
                        passwordFormatCorrect = false
                        errorIconDrawable = null
                        error = context.getString(R.string.password_no_whitespace)
                        boxStrokeErrorColor
                    } else if (password.length < 8) {
                        isErrorEnabled = true
                        passwordNotShort = false
                        errorIconDrawable = null
                        error = context.getString(R.string.password_8_chars)
                        boxStrokeErrorColor
                    } else if (!correctFormat) {
                        isErrorEnabled = true
                        errorIconDrawable = null
                        passwordFormatCorrect = false
                        error = context.getString(R.string.strong_password)
                        boxStrokeErrorColor
                    } else if (password.length > 20) {
                        isErrorEnabled = true
                        passwordNotLong = false
                        errorIconDrawable = null
                        error = context.getString(R.string.password_20_chars)
                        boxStrokeErrorColor
                    } else {
                        passwordFormatCorrect = true
                        passwordNotLong = true
                        passwordNotShort = true
                        isErrorEnabled = false
                    }
                }
                binding.signUpContinueEmailBt.isEnabled =
                    emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                            && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                            && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                            && usernameNotLong
            }
        }

        binding.signUpConfirmPasswordCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let {
                confirmPasswordFilled = it.isNotEmpty()
                binding.signUpConfirmPasswordCard.apply {
                    val passwordsMatch = it.toString() == password
                    if (!passwordsMatch) {
                        isErrorEnabled = true
                        errorIconDrawable = null
                        confirmedPasswordMatch = false
                        error = context.getString(R.string.passwords_not_match)
                        boxStrokeErrorColor
                    } else {
                        confirmedPasswordMatch = true
                        isErrorEnabled = false
                    }
                }
            }
            binding.signUpContinueEmailBt.isEnabled =
                emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                        && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                        && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                        && usernameNotLong
        }

        binding.signUpAgreementCb.setOnClickListener {
            conditionsChecked = binding.signUpAgreementCb.isChecked
            binding.signUpContinueEmailBt.isEnabled =
                emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                        && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                        && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                        && usernameNotLong
        }

        binding.signUpNameCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let { nameFilled = it.isNotEmpty() }
            binding.signUpNameCard.apply {
                if (text.toString().length > 15) {
                    isErrorEnabled = true
                    nameNotLong = false
                    errorIconDrawable = null
                    error = context.getString(R.string.name_long)
                    boxStrokeErrorColor
                } else {
                    nameNotLong = true
                    isErrorEnabled = false
                    endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
            binding.signUpContinueEmailBt.isEnabled =
                emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                        && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                        && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                        && usernameNotLong
        }

        binding.signUpLastNameCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let { lastNameFilled = it.isNotEmpty() }
            binding.signUpNameCard.apply {
                if (text.toString().length > 15) {
                    isErrorEnabled = true
                    lastNameNotLong = false
                    errorIconDrawable = null
                    error = context.getString(R.string.name_long)
                    boxStrokeErrorColor
                } else {
                    lastNameNotLong = true
                    isErrorEnabled = false
                    endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
            binding.signUpContinueEmailBt.isEnabled =
                emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                        && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                        && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                        && usernameNotLong
        }

        binding.signUpUsernameCard.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let { usernameFilled = it.isNotEmpty() }
            binding.signUpNameCard.apply {
                if (text.toString().length > 15) {
                    isErrorEnabled = true
                    usernameNotLong = false
                    errorIconDrawable = null
                    error = context.getString(R.string.name_long)
                    boxStrokeErrorColor
                } else {
                    usernameNotLong = true
                    isErrorEnabled = false
                    endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                }
            }
            binding.signUpContinueEmailBt.isEnabled =
                emailFormatCorrect && emailFilled && passwordFilled && passwordFormatCorrect && passwordNotShort
                        && passwordNotLong && confirmPasswordFilled && confirmedPasswordMatch && conditionsChecked
                        && nameFilled && nameNotLong && lastNameFilled && lastNameNotLong && usernameFilled
                        && usernameNotLong
        }


        binding.signUpContinueEmailBt.setOnClickListener {
            signUpPressed = true
            val email = binding.signUpEmailCard.editText?.text.toString()
            val password = binding.signUpPasswordCard.editText?.text.toString()
            val username = binding.signUpUsernameCard.editText?.text.toString()
            val name = binding.signUpNameCard.editText?.text.toString()
            val surname = binding.signUpLastNameCard.editText?.text.toString()
            val dateOfBirth = "1999-06-29T21:47:15.833"
            viewModel.signUp(email, password, dateOfBirth, name, surname, username)
        }

        binding.signUpLogInAccountTv.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading)
                binding.signUpProgressbar.visibility = View.VISIBLE
            else
                binding.signUpProgressbar.visibility = View.GONE
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner) {
            val name = it.data?.name
            name?.let {
                if(name.isNotEmpty()) {
                    findNavController().popBackStack()
                    Toast.makeText(context, "Успешна регистрација", Toast.LENGTH_SHORT).show()
                }
            }
        }
//        viewModel.signUpSuccessful.observe(viewLifecycleOwner) { signUpSuccessful ->  // Loading observable for the progress bar
//            if (signUpPressed) {
//                signUpPressed =
//                    false // Flag for login button pressed. The flag is there to make sure that when the observable is false
//                if (signUpSuccessful) { // not to execute code in else block
//                    findNavController().popBackStack()
////                    findNavController().navigate( // navigating to home screen after successful login
////                        R.id.vehiclesScreenFragment,
////                        bundleOf(),
////                        Utils.popUpLeftRightNavigation()
////                    )
////                    viewModel.signUpResponse.observe(viewLifecycleOwner) { signUpResponse ->
////                        with(sharedPrefs.edit()) {
////                            putString(Constants.USER_NAME, signUpResponse.data?.name)
////                            putString(Constants.USER_SURNAME, signUpResponse.data?.surname)
////                            signUpResponse.data?.id?.let { putInt(Constants.USER_ID, it) }
////                            apply()
////                        }
////                    }
//                } else {
//                    Toast.makeText(context, "Sign Up Error", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


    }

    private fun checkPassword(password: String): Boolean {
        var hasUppercase = false
        var hasLowercase = false
        var hasDigits = false
        val hasSpecialCharacter =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[.,=;+()\"#?!@$%^&'_{}|/<>*~:`-]).{8,}$")
                .matcher(password).find()
        var trueStatements = 0

        for (char in password) {
            when {
                char.isDigit() -> hasDigits = true
                char.isUpperCase() -> hasUppercase = true
                char.isLowerCase() -> hasLowercase = true
            }
        }
        if (hasDigits) trueStatements++
        if (hasUppercase) trueStatements++
        if (hasLowercase) trueStatements++
        if (hasSpecialCharacter) trueStatements++

        return trueStatements > 3
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}