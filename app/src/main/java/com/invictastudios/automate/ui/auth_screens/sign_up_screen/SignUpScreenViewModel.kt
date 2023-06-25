package com.invictastudios.automate.ui.auth_screens.sign_up_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.automate.data.api.api_services.ApiRepository
import com.invictastudios.automate.data.model.auth_models.LoginRequest
import com.invictastudios.automate.data.model.auth_models.SignUpRequest
import com.invictastudios.automate.data.model.auth_models.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
) : ViewModel() {

    private var getSignUpResponse: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signUpResponse: LiveData<SignUpResponse> = getSignUpResponse
    val loading = MutableLiveData(false)
    val signUpSuccessful = MutableLiveData(false)
    var loginMessage = ""

    fun signUp(
        email: String, password: String, dateOfBirth: String, name: String, surname: String,
        username: String
    ) {
        viewModelScope.launch {
            loading.value = true
            val response = apiRepository.signUpWithEmail(SignUpRequest(name, surname,dateOfBirth,username,email,password))
            response?.let {
                Log.d("TAG", "signUp: Vlegov Tuka i se e okej")
                getSignUpResponse.value = it
                signUpSuccessful.value = true
            }
            loading.value = false
        }
    }

}