package com.invictastudios.automate.data.model.vehicle_models

import com.google.gson.annotations.SerializedName

data class VehicleModel(
    @SerializedName("data")
    var data: List<VehicleData>?,
)

data class VehicleDetail(
    @SerializedName("data")
    var data: VehicleData?,
)

data class PerformedServicesModel(
    @SerializedName("data")
    var data: PerformedServicesData?,
)

data class VehicleData(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("vin")
    var vehicleIdNumber: String?,
    @SerializedName("registrationPlate")
    var registrationPlate: String?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("width")
    var weight: Int?,
    @SerializedName("currentMileage")
    var currentMileage: Int?,
    @SerializedName("nextServiceMileage")
    var nextServiceMileage: Int?,
    @SerializedName("performedServices")
    var performedServices: List<PerformedServices>?,
    @SerializedName("ownerId")
    var ownerId: Int?,
    @SerializedName("modelId")
    var modelId: Int?,
    @SerializedName("model")
    var model: Model?,
)

data class PerformedServices(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("price")
    var price: Price?,
    @SerializedName("performedOn")
    var performedOn: String?,
    @SerializedName("mileage")
    var mileage: String?,
    @SerializedName("service")
    var service: Service?,
    @SerializedName("replacements")
    var replacements: List<Replacements>?,

)

data class Replacements(
    @SerializedName("price")
    var price: Price?,
    @SerializedName("part")
    var part: Part?,
)

data class Part(
    @SerializedName("code")
    var code: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("description")
    var description: String?,
)

data class Service(
    @SerializedName("name")
    var name: String?,
)

data class Price(
    @SerializedName("value")
    var value: Int?,
    @SerializedName("currency")
    var currency: Int?,
)

data class Model(
    @SerializedName("name")
    var name: String?,
    @SerializedName("year")
    var year: Int?,
    @SerializedName("brand")
    var brand: Brand?,
)

data class Brand(
    @SerializedName("name")
    var name: String?,
)


data class PerformedServicesData(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("price")
    var price: Price?,
    @SerializedName("performedOn")
    var performedOn: String?,
    @SerializedName("mileage")
    var mileage: String?,
    @SerializedName("service")
    var service: Service?,
    @SerializedName("replacements")
    var replacements: List<Replacements>?,
    @SerializedName("serviceStation")
    var serviceStation: ServiceStation?,
)

data class ServiceStation(
    @SerializedName("name")
    var name: String?,
    @SerializedName("invoices")
    var invoices: List<Invoices>?,
)

data class Invoices(
    @SerializedName("id")
    var id: Int?,
)
