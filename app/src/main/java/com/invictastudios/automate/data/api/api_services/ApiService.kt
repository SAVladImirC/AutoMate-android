package com.invictastudios.automate.data.api.api_services

import com.invictastudios.automate.data.model.auth_models.LoginResponse
import com.invictastudios.automate.data.model.auth_models.SignUpResponse
import com.invictastudios.automate.data.model.vehicle_models.PerformedServicesModel
import com.invictastudios.automate.data.model.vehicle_models.VehicleDetail
import com.invictastudios.automate.data.model.vehicle_models.VehicleModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

//    @GET("/wp-json/tct-trips/v1/trip-details/{entryType}/{directusId}")
//    suspend fun getTripDetails(
//        @Path("entryType") entryType: String,
//        @Path("directusId") directusId: String,
//        @Query("currency") currency: String
//    ): Response<TripItemPageDetails>
//
//    @GET("wp-json/theculturetrip/v3/hubs/{tripType}")
//    suspend fun getAllTrips(
//        @Path("tripType") entryType: String,
//        @Query("currency") currency: String
//    ): Response<Result>
//
    @Headers("Content-Type: application/json")
    @POST("api/users/login")
    suspend fun loginWithEmail(
        @Body body: String
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/users/register")
    suspend fun signUpWithEmail(
        @Body body: String
    ): Response<SignUpResponse>

    @GET("api/vehicle/user/{userId}")
    suspend fun getUserVehicles(
        @Path("userId") userId: String,
    ): Response<VehicleModel>

    @GET("api/vehicle/{vehicleId}")
    suspend fun getVehicleInfo(
        @Path("vehicleId") vehicleId: String,
    ): Response<VehicleDetail>

    @GET("api/PerformedService/{serviceId}")
    suspend fun getServiceInfo(
        @Path("serviceId") serviceId: String,
    ): Response<PerformedServicesModel>
}
