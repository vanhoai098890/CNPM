package com.example.roomrentalapplication.ui.edit_profile

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.repositories.CustomerRepository
import com.example.roomrentalapplication.extensions.bindError
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onError
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    localSessionManager: LoginSessionManager,
    private val customerRepository: CustomerRepository
) : BaseViewModel() {

    val stateCustomerProperty: MutableStateFlow<CustomerProperty?> = MutableStateFlow(null)
    val stateUpdateSuccess = MutableStateFlow(false)
    val stateUpdateError = MutableStateFlow(false)

    init {
        stateCustomerProperty.value = localSessionManager.getCustomerLocal()
    }

    fun submitPersonalInfo() {
        stateCustomerProperty.value?.let {
            customerRepository.updateCustomerById(it).bindLoading(this)
                .bindError(this).onSuccess {
                    stateUpdateSuccess.value = true
                }.onError {
                    stateUpdateError.value = true
                }.launchIn(viewModelScope)
        }
    }

}
