package com.invictastudios.automate.data.model.auth_models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("userNameOrEmail")
    var email: String?,
    @SerializedName("password")
    var password: String?,
)
