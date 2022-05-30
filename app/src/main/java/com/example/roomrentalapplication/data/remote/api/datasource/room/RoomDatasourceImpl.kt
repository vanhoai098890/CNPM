package com.example.roomrentalapplication.data.remote.api.datasource.room

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse
import com.example.roomrentalapplication.data.remote.exception.apiCall
import javax.inject.Inject

class RoomDatasourceImpl @Inject constructor(private val apiService: ApiService) : RoomDatasource {
    override suspend fun getRoomByPropertyId(propertyId: Int): RoomResponse {
        return apiCall {
            apiService.getRoomsByPropertyId(propertyId)
        }
    }
}
