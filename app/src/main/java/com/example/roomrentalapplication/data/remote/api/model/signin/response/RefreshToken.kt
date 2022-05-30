package com.example.roomrentalapplication.data.remote.api.model.signin.response

import com.google.gson.annotations.SerializedName

data class RefreshToken(@SerializedName("token") val token: String)
