package com.example.roomrentalapplication.data.remote.api.model.date_rent

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

class DateStatusResponse(
    @SerializedName("data")
    val data: List<DateStatus>
) : CommonResponse()

data class DateStatus(val day: Int, val status: Boolean)
