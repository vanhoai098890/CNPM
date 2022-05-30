package com.example.roomrentalapplication.data.remote.api.datasource.customer

import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerPropertyResponse

interface CustomerDatasource {
    suspend fun getCustomerById(customerId: Int): CustomerPropertyResponse
}
