package com.example.roomrentalapplication.data.remote.api.datasource.room

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomSavedRequest
import com.example.roomrentalapplication.data.remote.api.model.status_saved.StatusSavedResponse
import com.example.roomrentalapplication.data.remote.exception.apiCall
import javax.inject.Inject

class RoomDatasourceImpl @Inject constructor(private val apiService: ApiService) : RoomDatasource {
    override suspend fun getRoomByPropertyId(propertyId: Int): RoomResponse {
        return apiCall {
            apiService.getRoomsByPropertyId(propertyId)
        }
    }

    override suspend fun saveRoom(roomSavedRequest: RoomSavedRequest): CommonResponse {
        return apiCall {
            apiService.saveRoom(roomSavedRequest)
        }
    }

    override suspend fun deleteRoom(roomSavedRequest: RoomSavedRequest): CommonResponse {
        return apiCall {
            apiService.deleteRoom(roomSavedRequest)
        }
    }

    override suspend fun getStatusSaved(roomSavedRequest: RoomSavedRequest): StatusSavedResponse {
        return apiCall {
            apiService.getStatusSavedRoom(roomSavedRequest.customerId, roomSavedRequest.roomId)
        }
    }
}
