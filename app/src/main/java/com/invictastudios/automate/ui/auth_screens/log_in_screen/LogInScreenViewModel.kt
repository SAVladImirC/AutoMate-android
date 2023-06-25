package com.invictastudios.automate.ui.auth_screens.log_in_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.automate.data.api.api_services.ApiRepository
import com.invictastudios.automate.data.model.auth_models.LoginRequest
import com.invictastudios.automate.data.model.auth_models.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private var getLoginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginResponse: LiveData<LoginResponse> = getLoginResponse
    val loading = MutableLiveData(false)
    val loginSuccessful = MutableLiveData(false)
    var loginMessage = ""

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loading.value = true
            val response = apiRepository.loginWithEmail(LoginRequest(email,password))
            response?.let {
                getLoginResponse.value = it
                response.data?.name?.let { name ->
                    if(name.isNotEmpty())
                        loginSuccessful.value = true
                }
            }
            loading.value = false
        }
    }

}