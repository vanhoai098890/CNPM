package com.example.roomrentalapplication.data.remote

import com.example.roomrentalapplication.data.remote.api.model.refreshtoken.RefreshTokenRequest
import com.example.roomrentalapplication.data.remote.api.model.refreshtoken.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("v1/authentication/refresh-token")
    fun postRefreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Call<RefreshTokenResponse>
}
