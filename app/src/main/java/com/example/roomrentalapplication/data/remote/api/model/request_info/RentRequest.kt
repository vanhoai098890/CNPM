package com.example.roomrentalapplication.data.remote.api.model.request_info

data class RentRequest(
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
