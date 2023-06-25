package com.invictastudios.automate.data.model.auth_models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("data")
    var data: LoginData?,
    @SerializedName("message")
    var message: String?,
)
