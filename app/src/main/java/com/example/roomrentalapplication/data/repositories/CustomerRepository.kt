package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.remote.api.datasource.customer.CustomerDatasourceImpl
import com.example.roomrentalapplication.extensions.safeFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor(private val customerDatasourceImpl: CustomerDatasourceImpl) {
    fun getCustomerById(customerId: Int) = safeFlow {
        customerDatasourceImpl.getCustomerById(customerId)
    }
}
