package com.example.roomrentalapplication.data.remote.api.datasource.favourite

import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse

interface FavouriteDatasource {
    suspend fun getFavouriteRooms(customerId: Int):RoomResponse
}