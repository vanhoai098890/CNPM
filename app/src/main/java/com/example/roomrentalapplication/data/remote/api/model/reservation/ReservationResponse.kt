package com.example.roomrentalapplication.data.remote.api.model.reservation

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class ReservationResponse(
    @SerializedName("data")
    val data: ReservationItem
) : CommonResponse()

data class ReservationItem(
    val roomId: Int?,
    val customerId: Int?,
    val createDate: String?,
    val startDate: String?,
    val endDate: String?,
    val quantity: Int? = 1,
    val reservationStatusId: Int? = 1,
    val total: Double?,
    val deposit: Double?,
)
