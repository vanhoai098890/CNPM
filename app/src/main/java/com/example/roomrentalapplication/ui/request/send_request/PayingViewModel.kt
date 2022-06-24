package com.example.roomrentalapplication.ui.request.send_request

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItem
import com.example.roomrentalapplication.data.repositories.CustomerRepository
import com.example.roomrentalapplication.data.repositories.DateRentRepository
import com.example.roomrentalapplication.extensions.onError
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PayingViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val dateRentRepository:DateRentRepository
) : BaseViewModel() {
    val stateCustomerProperty = MutableStateFlow(CustomerProperty())
    val stateReceivedRequestItem: MutableStateFlow<ReceivedRequestItem?> = MutableStateFlow(null)
    val stateVisibleButton = MutableStateFlow(true)
    val stateSuccess = MutableStateFlow(false)
    val stateError = MutableStateFlow(false)

    fun getCustomerById(id: Int) = customerRepository.getCustomerById(id).onSuccess {
        stateCustomerProperty.value = it.data
    }.launchIn(viewModelScope)

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
