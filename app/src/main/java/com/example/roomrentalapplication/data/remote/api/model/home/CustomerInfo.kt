package com.example.roomrentalapplication.data.remote.api.model.home

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class CustomerInfo(
    val username: String,
    @SerializedName("address")
    val address: String?,
    @SerializedName("avatar")
    var avatar: String? = "",
    @SerializedName("birthday")
    val birthDay: String,
    @SerializedName("fullname")
    var fullname: String,
) : CommonResponse()
