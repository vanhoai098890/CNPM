package com.example.roomrentalapplication.data.remote.api.datasource.property

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertiesItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomInsertRequest
import okhttp3.MultipartBody

interface PropertyDatasource {
    suspend fun getAllProperty(): PropertyResponse

    suspend fun getDetailProperty(propertyId: Int): PropertyItemResponse

    suspend fun getPropertiesById(customerId:Int): PropertiesItemResponse

    suspend fun getProperty(
        city: String,
        typeId: Int,
        name: String
    ): PropertyResponse

    suspend fun insertProperty(
        customerId: PropertyItem, imagePart: List<MultipartBody.Part>
    ): CommonResponse

    suspend fun insertRoom(
        roomWithPriceDto: RoomInsertRequest, imagePart: List<MultipartBody.Part>
    ): CommonResponse
}
