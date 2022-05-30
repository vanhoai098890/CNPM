package com.example.roomrentalapplication.data.remote.api.model.signin.request

import com.google.gson.annotations.SerializedName

data class SignInRequestDto(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
