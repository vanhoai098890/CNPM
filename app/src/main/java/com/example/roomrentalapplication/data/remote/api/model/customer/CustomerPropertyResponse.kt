package com.example.roomrentalapplication.data.remote.api.model.customer

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class CustomerPropertyResponse(
    @SerializedName("data")
    val data: CustomerProperty
) : CommonResponse()

data class CustomerProperty(
    @SerializedName("customerId")
    val customerId: Int? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("customerName")
    val customerName: String? = null,
    @SerializedName("citizenId")
    val citizenId: String? = null,
    @SerializedName("birthday")
    val birthday: String? = null,
    @SerializedName("nationality")
    val nationality: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
)
