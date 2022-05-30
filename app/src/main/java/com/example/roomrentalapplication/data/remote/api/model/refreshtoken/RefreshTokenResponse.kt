package com.example.roomrentalapplication.data.remote.api.model.refreshtoken

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("Data")
    val data: RefreshTokenData
) : CommonResponse()

data class RefreshTokenData(
    @SerializedName("AuthenticationResult")
    val refreshAuthenticationResult: RefreshAuthenticationResult?
)

data class RefreshAuthenticationResult(
    @SerializedName("AccessToken") val accessToken: String,

    @SerializedName("ExpiresIn") val expiresIn: Long,

    @SerializedName("TokenType") val tokenType: String,

    @SerializedName("IdToken") val idToken: String
)
