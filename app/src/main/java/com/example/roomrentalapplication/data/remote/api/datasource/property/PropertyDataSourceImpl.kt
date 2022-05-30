package com.example.roomrentalapplication.data.remote.api.datasource.property

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyResponse
import com.example.roomrentalapplication.data.remote.exception.apiCall
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

    override suspend fun getProperty(
        city: String,
        typeId: Int,
        name: String
    ): PropertyResponse {
        return apiCall {
            apiService.getRoomsByPropertyWithName(city, typeId, name)
        }
    }
}
