package com.example.roomrentalapplication.data.remote.api.datasource.property

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertiesItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomInsertRequest
import com.example.roomrentalapplication.data.remote.exception.apiCall
import okhttp3.MultipartBody
import javax.inject.Inject

class PropertyDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    PropertyDatasource {
    override suspend fun getAllProperty(): PropertyResponse = apiCall {
        apiService.getAllProperty()
    }

    override suspend fun getDetailProperty(propertyId: Int): PropertyItemResponse {
        return apiCall {
            apiService.getPropertyById(propertyId)
        }
    }

    override suspend fun getPropertiesById(customerId: Int): PropertiesItemResponse {
        return apiCall {
            apiService.getPropertiesByCustomerId(customerId)
        }
    }

    override suspend fun getProperty(
        city: String,
        typeId: Int,
        name: String
    ): PropertyResponse {
        return apiCall {
            apiService.getRoomsByPropertyWithName(city, typeId, name)
        }
    }

    override suspend fun insertProperty(
        customerId: PropertyItem,
        imagePart: List<MultipartBody.Part>
    ): CommonResponse {
        return apiCall {
            apiService.insertProperty(customerId, imagePart.toTypedArray())
        }
    }

    override suspend fun insertRoom(
        roomWithPriceDto: RoomInsertRequest,
        imagePart: List<MultipartBody.Part>
    ): CommonResponse {
        return apiCall {
            apiService.insertRoom(roomWithPriceDto, imagePart.toTypedArray())
        }
    }
}
