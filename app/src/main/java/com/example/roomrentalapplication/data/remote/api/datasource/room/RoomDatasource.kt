package com.example.roomrentalapplication.data.remote.api.datasource.room

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomSavedRequest
import com.example.roomrentalapplication.data.remote.api.model.status_saved.StatusSavedResponse

interface RoomDatasource {
    suspend fun getRoomByPropertyId(propertyId: Int): RoomResponse
    suspend fun saveRoom(roomSavedRequest: RoomSavedRequest): CommonResponse
    suspend fun deleteRoom(roomSavedRequest: RoomSavedRequest): CommonResponse
    suspend fun getStatusSaved(roomSavedRequest: RoomSavedRequest): StatusSavedResponse
}
