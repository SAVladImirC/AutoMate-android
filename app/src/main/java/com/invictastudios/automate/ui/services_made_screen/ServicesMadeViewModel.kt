package com.invictastudios.automate.ui.services_made_screen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.automate.data.api.api_services.ApiRepository
import com.invictastudios.automate.data.model.vehicle_models.VehicleDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesMadeViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private var getVehicleInfoResponse: MutableLiveData<VehicleDetail> = MutableLiveData()
    val vehicleInfoResponse: LiveData<VehicleDetail> = getVehicleInfoResponse
    val loading = MutableLiveData(false)


    fun getUserVehicles(vehicleId:String) {
        viewModelScope.launch {
            loading.value = true
            val response = apiRepository.getVehicleInfo(vehicleId)
            response?.let {
                getVehicleInfoResponse.value = it
            }
            loading.value = false
        }
    }
}