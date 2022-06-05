package com.example.roomrentalapplication.data.remote.api.model.date_rent

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class PriceRentResponse(
    @SerializedName("data")
    val price: Double
) : CommonResponse()
