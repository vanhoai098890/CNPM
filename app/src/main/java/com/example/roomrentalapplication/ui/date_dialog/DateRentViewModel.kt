package com.example.roomrentalapplication.ui.date_dialog

import android.annotation.SuppressLint
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.data.remote.api.model.date_rent.PriceRentResponse
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.data.repositories.DateRentRepository
import com.example.roomrentalapplication.extensions.FlowResult
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DateRentViewModel @Inject constructor(
    private val dateRentRepository: DateRentRepository
) : BaseViewModel() {

    var stateEnablePayButton = MutableStateFlow(false)


    fun getDateStatusByRoomId(roomId: Int, month: Int, year: Int) =
        dateRentRepository.getDateStatusByRoomId(roomId, month, year)

    fun getDateFurthest(roomId: Int, date: String) =
        dateRentRepository.getDateFurthestByRoomId(roomId, date)

    @SuppressLint("SimpleDateFormat")
    fun getPrice(priceId: Int, dateStart: GregorianCalendar, dateEnd: GregorianCalendar):
            Flow<FlowResult<PriceRentResponse>> {
        dateEnd.add(Calendar.DAY_OF_MONTH, 1)
        return dateRentRepository.getPriceRent(
            priceId,
            SimpleDateFormat(AppConstant.FORMAT_DATE).format(dateStart.time),
            SimpleDateFormat(AppConstant.FORMAT_DATE).format(dateEnd.time)
        )
    }
}
