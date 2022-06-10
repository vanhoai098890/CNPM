package com.example.roomrentalapplication.data.remote.api.datasource.date_rent

import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateFurthestResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateStatusResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.PriceRentResponse
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentResponse

interface DateRentDatasource {
    suspend fun getDateStatusByRoomId(roomId: Int, month: Int, year: Int): DateStatusResponse

    suspend fun getDateFurthestByRoomId(
        roomId: Int, date: String
    ): DateFurthestResponse

    suspend fun getPrice(
        priceId: Int, dateStart: String, endStart: String
    ): PriceRentResponse

    suspend fun confirmRentRoom(
        rentRequest: RentRequest
    ): RentResponse
}
