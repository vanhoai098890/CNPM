package com.example.roomrentalapplication.data.remote.api.datasource.room

import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse

interface RoomDatasource {
    suspend fun getRoomByPropertyId(propertyId: Int): RoomResponse
}
