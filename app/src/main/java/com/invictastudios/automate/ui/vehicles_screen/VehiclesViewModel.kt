package com.invictastudios.automate.ui.vehicles_screen


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.automate.data.api.api_services.ApiRepository
import com.invictastudios.automate.data.model.auth_models.SignUpRequest
import com.invictastudios.automate.data.model.auth_models.SignUpResponse
import com.invictastudios.automate.data.model.vehicle_models.VehicleData
import com.invictastudios.automate.data.model.vehicle_models.VehicleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private var getUserVehiclesResponse: MutableLiveData<VehicleModel> = MutableLiveData()
    val userVehiclesResponse: LiveData<VehicleModel> = getUserVehiclesResponse
    val loading = MutableLiveData(false)


    fun getUserVehicles(userId:String) {
        viewModelScope.launch {
            loading.value = true
            val response = apiRepository.getUserVehicles(userId)
            response?.let {
                getUserVehiclesResponse.value = it
            }
            loading.value = false
        }
    }
}