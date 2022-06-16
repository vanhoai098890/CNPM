package com.example.roomrentalapplication.data.remote.api.datasource.customer

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerPropertyResponse

interface CustomerDatasource {
    suspend fun getCustomerById(customerId: Int): CustomerPropertyResponse
    suspend fun updateCustomerById(
        customerId: Int, customerRequest: CustomerProperty
    ): CommonResponse
}
