package com.invictastudios.automate.data.model.auth_models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    var data: LoginData?,
    @SerializedName("message")
    var message: String?,
)

data class LoginData(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("surname")
    var surname: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("email")
    var email: String?,
)
