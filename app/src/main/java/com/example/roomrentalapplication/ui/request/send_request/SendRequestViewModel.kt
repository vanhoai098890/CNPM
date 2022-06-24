package com.example.roomrentalapplication.ui.request.send_request

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItem
import com.example.roomrentalapplication.data.repositories.DateRentRepository
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SendRequestViewModel @Inject constructor(
    private val loginSessionManager: LoginSessionManager,
    private val dateRentRepository: DateRentRepository
) : BaseViewModel() {

    val customer: MutableStateFlow<CustomerProperty?> = MutableStateFlow(null)
    val stateListItem: MutableStateFlow<MutableList<ReceivedRequestItem>> = MutableStateFlow(
        mutableListOf()
    )
    val stateVisibleNotFound = MutableStateFlow(false)


    init {
        getCustomerInfo()
    }

    private fun getCustomerInfo() {
        val result = loginSessionManager.getCustomerLocal()
        customer.value = result
    }

    fun getSendReservation() {
        customer.value?.apply {
            customerId?.let {
                dateRentRepository.getSendReservation(it).onSuccess { response ->
                    stateListItem.value = response.data as MutableList<ReceivedRequestItem>
                    if (stateListItem.value.size == 0) {
                        stateVisibleNotFound.value = true
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
