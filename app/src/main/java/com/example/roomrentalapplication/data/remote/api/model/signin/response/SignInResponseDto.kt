package com.example.roomrentalapplication.data.remote.api.model.signin.response

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class SignInResponseDto(
    @SerializedName("data")
    val loginAuthenticator: LoginAuthenticator
) : CommonResponse()
