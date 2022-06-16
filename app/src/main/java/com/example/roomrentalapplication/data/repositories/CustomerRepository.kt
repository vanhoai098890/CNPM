package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.datasource.customer.CustomerDatasourceImpl
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.extensions.safeFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor(
    private val customerDatasourceImpl: CustomerDatasourceImpl,
    private val loginSessionManager: LoginSessionManager
) {
    fun getCustomerById(customerId: Int) = safeFlow {
        customerDatasourceImpl.getCustomerById(customerId)
    }

    fun updateCustomerById(customerRequest: CustomerProperty) = safeFlow {
        customerDatasourceImpl.updateCustomerById(
            customerId = customerRequest.customerId ?: 0,
            customerRequest
        )
    }.onSuccess {
        loginSessionManager.saveCustomer(customerRequest)
    }
}
