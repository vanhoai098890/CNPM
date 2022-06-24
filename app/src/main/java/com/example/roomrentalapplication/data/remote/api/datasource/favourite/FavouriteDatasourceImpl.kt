package com.example.roomrentalapplication.data.remote.api.datasource.favourite

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateFurthestResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateStatusResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.PriceRentResponse
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItemResponse
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentResponse
import com.example.roomrentalapplication.data.remote.api.model.reservation.ReservationResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse
import com.example.roomrentalapplication.data.remote.exception.apiCall
import javax.inject.Inject

class FavouriteDatasourceImpl @Inject constructor(private val apiService: ApiService) :
    FavouriteDatasource {

    override suspend fun getFavouriteRooms(customerId: Int): RoomResponse {
        return apiCall {
            apiService.getFavouriteRoom(customerId)
        }
    }
}
