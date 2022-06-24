package com.example.roomrentalapplication.ui.request.received_request

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItem
import com.example.roomrentalapplication.data.remote.api.model.reservation.ReservationItem
import com.example.roomrentalapplication.data.repositories.CustomerRepository
import com.example.roomrentalapplication.data.repositories.DateRentRepository
import com.example.roomrentalapplication.extensions.*
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ReceivedDetailViewModel @Inject constructor(
    private val dateRentRepository: DateRentRepository,
    private val customerRepository: CustomerRepository
) : BaseViewModel() {

    val stateReceivedRequestItem: MutableStateFlow<ReceivedRequestItem?> = MutableStateFlow(null)
    val stateCustomerProperty: MutableStateFlow<CustomerProperty?> = MutableStateFlow(null)
    val stateReservationItem: MutableStateFlow<ReservationItem?> = MutableStateFlow(null)
    val stateDayRents = MutableStateFlow("0")
    val stateVisibleButton = MutableStateFlow(true)
    val stateSuccess = MutableStateFlow(false)
    val stateError = MutableStateFlow(false)

    fun getReservation(id: Int) {
        dateRentRepository.getReservation(id = id).bindLoading(this).onSuccess {
            stateReservationItem.value = it.data
            it.data.startDate?.let { startDate ->
                it.data.endDate?.let { endDate ->
                    stateDayRents.value = startDate.getGregorianCalendar()
                        .calcDatesWith(endDate.getGregorianCalendar()).toString()
                }
            }
            //Pending
            stateVisibleButton.value = it.data.reservationStatusId == 1
        }.launchIn(viewModelScope)
    }

    fun getCustomer(id: Int) {
        customerRepository.getCustomerById(id).bindLoading(this).onSuccess {
            stateCustomerProperty.value = it.data
        }.launchIn(viewModelScope)
    }

    fun updateStatusReservation( status: Int) {
        stateReceivedRequestItem.value?.apply {
            dateRentRepository.updateReservationStatus(reservationId, status).onSuccess {
                stateVisibleButton.value = false
                stateSuccess.value = true
            }.onError {
                stateVisibleButton.value = true
                stateError.value = true
            }.launchIn(viewModelScope)
        }
    }
}
