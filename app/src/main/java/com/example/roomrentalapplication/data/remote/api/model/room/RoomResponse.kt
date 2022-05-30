package com.example.roomrentalapplication.data.remote.api.model.room

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.property.ImageItem
import com.google.gson.annotations.SerializedName

data class RoomResponse(
    @SerializedName("data")
    val data: List<RoomItem>
) : CommonResponse()

data class RoomItem(
    val roomId: Int?,
    val propertyId: Int?,
    val monthPrice: Double?,
    val roomStatusId: Int?,
    val imageStorageId: Int?,
    val bedrooms: Int?,
    val roomName: String?,
    val size: String?,
    val capacity: String?,
    val images: List<ImageItem>?,
    val description: String?
)
