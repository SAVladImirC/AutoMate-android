package com.invictastudios.automate.data.api.api_services

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.invictastudios.automate.data.api.SafeApiRequest
import com.invictastudios.automate.data.model.auth_models.LoginRequest
import com.invictastudios.automate.data.model.auth_models.LoginResponse
import com.invictastudios.automate.data.model.auth_models.SignUpRequest
import com.invictastudios.automate.data.model.auth_models.SignUpResponse
import com.invictastudios.automate.data.model.vehicle_models.PerformedServicesModel
import com.invictastudios.automate.data.model.vehicle_models.VehicleDetail
import com.invictastudios.automate.data.model.vehicle_models.VehicleModel

import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService
) : SafeApiRequest() {

    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var isUnauthorizedError: MutableLiveData<Boolean> = MutableLiveData()

//    suspend fun getTripDetails(
//        entryType: String,
//        directusId: String,
//        currency: String
//    ): TripItemPageDetails? {
//        return apiRequest {
//            apiService.getTripDetails(entryType, directusId, currency)
//        }
//    }
//
//    suspend fun getAllTrips(entryType: String, currency: String): Result? {
//        return apiRequest {
//            apiService.getAllTrips(entryType, currency)
//        }
//    }
//
    suspend fun loginWithEmail(loginModel: LoginRequest): LoginResponse? {
        return apiRequest {
            apiService.loginWithEmail(Gson().toJson(loginModel))
        }
    }

    suspend fun signUpWithEmail(signUpModel: SignUpRequest): SignUpResponse? {
        return apiRequest {
            apiService.signUpWithEmail(Gson().toJson(signUpModel))
        }
    }

    suspend fun getUserVehicles(userId: String): VehicleModel? {
        return apiRequest {
            apiService.getUserVehicles(userId)
        }
    }

    suspend fun getVehicleInfo(vehicleId: String): VehicleDetail? {
        return apiRequest {
            apiService.getVehicleInfo(vehicleId)
        }
    }

    suspend fun getServicesInfo(serviceId: String): PerformedServicesModel? {
        return apiRequest {
            apiService.getServiceInfo(serviceId)
        }
    }
//
//    suspend fun getTripTypes(): TripTypes? {
//        return apiRequest {
//            apiService.getTripTypes()
//        }
//    }
//
//    suspend fun getSearchTrips(): TripSearchObject? {
//        return apiRequest {
//            apiService.getSearchTrips()
//        }
//    }

    override fun exception(errorMsg: String, isUnauthorized: Boolean) {
        isUnauthorizedError.value = isUnauthorized
        errorMessage.value = errorMsg
    }
}
