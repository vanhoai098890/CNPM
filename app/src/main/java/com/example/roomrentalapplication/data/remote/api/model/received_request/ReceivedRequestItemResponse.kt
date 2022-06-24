package com.example.roomrentalapplication.data.remote.api.model.received_request

import android.os.Parcelable
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ReceivedRequestItemResponse(
    @SerializedName("data")
    val data: List<ReceivedRequestItem>
) : CommonResponse()

@Parcelize
data class ReceivedRequestItem(
    val createDate: String = "22/06/2022",
    val image: String = "",
    val roomName: String = "Spacious bedroom (Two gates)",
    val total: Double = 23.03,
    val status: String = "Approved",
    val startDate: String = "23/06/2022",
    val endDate: String = "25/06/2022",
    val reservationId: Int = 55,
    val customerId: Int = 6
) : Parcelable
