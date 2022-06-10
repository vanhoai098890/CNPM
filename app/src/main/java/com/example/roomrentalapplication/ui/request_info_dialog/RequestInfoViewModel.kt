package com.example.roomrentalapplication.ui.request_info_dialog

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.data.repositories.DateRentRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onError
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class RequestInfoViewModel @Inject constructor(
    private val loginSessionManager: LoginSessionManager,
    private val dateRentRepository: DateRentRepository
) : BaseViewModel() {
    val stateOnSuccessRentAction = MutableStateFlow(false)
    val stateOnErrorAction = MutableStateFlow(false)
    val customer: MutableStateFlow<CustomerProperty?> = MutableStateFlow(null)
    val firstDate = MutableStateFlow("")
    val secondDate = MutableStateFlow("")
    val total = MutableStateFlow(0.0)
    val deposit = MutableStateFlow(0.0)

    fun getCustomerInfo(): CustomerProperty? {
        val result = loginSessionManager.getCustomerLocal()
        customer.value = result
        return result
    }

    fun confirmRentRoom(rentRequest: RentRequest) =
        dateRentRepository.confirmRentRoom(rentRequest).bindLoading(this).onError {
            stateOnErrorAction.value = true
        }.onSuccess {
            stateOnSuccessRentAction.value = true
        }.launchIn(viewModelScope)
}
