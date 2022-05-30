package com.example.roomrentalapplication.data.remote.api.model.signin.response

import com.google.gson.annotations.SerializedName

data class LoginAuthenticator(
    @SerializedName("jwtToken")
    var jwtToken: String,
    @SerializedName("refreshToken")
    val refreshToken: RefreshToken?
)
