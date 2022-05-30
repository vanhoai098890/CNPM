package com.example.roomrentalapplication.data.remote.api.datasource.property

import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyResponse

interface PropertyDatasource {
    suspend fun getAllProperty(): PropertyResponse
    suspend fun getDetailProperty(propertyId: Int): PropertyItemResponse
    suspend fun getProperty(
        city: String,
        typeId: Int,
        name: String
    ): PropertyResponse
}
