package com.example.roomrentalapplication.data.remote.api.model.date_rent

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

class DateStatusResponse(
    @SerializedName("data")
    val data: List<DateStatus>
) : CommonResponse()

data class DateStatus(val day: Int, val status: Boolean) {

    override fun equals(other: Any?): Boolean {
        if (other is DateStatus) {
            return day == other.day && status == other.status
        }
        return false
    }

    override fun hashCode(): Int {
        return day
    }
}
