package com.example.roomrentalapplication.data.remote.api.datasource.date_rent

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateFurthestResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateStatusResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.PriceRentResponse
import com.example.roomrentalapplication.data.remote.exception.apiCall
import java.util.*
import javax.inject.Inject

class DateRentDatasourceImpl @Inject constructor(private val apiService: ApiService) :
    DateRentDatasource {
    override suspend fun getDateStatusByRoomId(
        roomId: Int,
        month: Int,
        year: Int
    ): DateStatusResponse {
        return apiCall {
            apiService.getDateStatusByRoomId(roomId, month, year)
        }
    }

    override suspend fun getDateFurthestByRoomId(roomId: Int, date: Date): DateFurthestResponse {
        return apiCall {
            apiService.getDateFurthestByRoomId(roomId, date)
        }
    }

    override suspend fun getPrice(
        priceId: Int,
        dateStart: String,
        endStart: String
    ): PriceRentResponse {
        return apiCall {
            apiService.getPrice(priceId, dateStart, endStart)
        }
    }
}
