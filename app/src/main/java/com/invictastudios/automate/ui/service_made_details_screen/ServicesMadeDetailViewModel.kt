package com.invictastudios.automate.ui.service_made_details_screen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.automate.data.api.api_services.ApiRepository
import com.invictastudios.automate.data.model.vehicle_models.PerformedServicesModel
import com.invictastudios.automate.data.model.vehicle_models.VehicleDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesMadeDetailViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private var getServicesInfoResponse: MutableLiveData<PerformedServicesModel> = MutableLiveData()
    val servicesInfoResponse: LiveData<PerformedServicesModel> = getServicesInfoResponse
    val loading = MutableLiveData(false)


    fun getServicesDetail(servicesDetailId:String) {
        viewModelScope.launch {
            loading.value = true
            val response = apiRepository.getServicesInfo(servicesDetailId)
            response?.let {
                getServicesInfoResponse.value = it
            }
            loading.value = false
        }
    }
}