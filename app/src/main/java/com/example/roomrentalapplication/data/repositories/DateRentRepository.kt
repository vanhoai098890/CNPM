package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.remote.api.datasource.date_rent.DateRentDatasourceImpl
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.extensions.safeFlow
import javax.inject.Inject

class DateRentRepository @Inject constructor(private val dateRentDatasourceImpl: DateRentDatasourceImpl) {
    fun getDateStatusByRoomId(roomId: Int, month: Int, year: Int) = safeFlow {
        dateRentDatasourceImpl.getDateStatusByRoomId(roomId, month, year)
    }

    fun getDateFurthestByRoomId(roomId: Int, date: String) = safeFlow {
        dateRentDatasourceImpl.getDateFurthestByRoomId(roomId, date)
    }

    fun getPriceRent(priceId: Int, startDate: String, endDate: String) = safeFlow {
        dateRentDatasourceImpl.getPrice(priceId, startDate, endDate)
    }

    fun confirmRentRoom(rentRequest: RentRequest) = safeFlow {
        dateRentDatasourceImpl.confirmRentRoom(rentRequest)
    }

    fun getReservation(id: Int) = safeFlow {
        dateRentDatasourceImpl.getReservation(id)
    }

    fun updateReservationStatus(reservationId: Int, status: Int) = safeFlow {
        dateRentDatasourceImpl.updateReservation(reservationId, status)
    }

    fun getSendReservation(customerId: Int) = safeFlow {
        dateRentDatasourceImpl.getSendReservation(customerId)
    }

    fun getReceivedReservation(customerId: Int) = safeFlow {
        dateRentDatasourceImpl.getReceivedReservation(customerId)
    }
}
