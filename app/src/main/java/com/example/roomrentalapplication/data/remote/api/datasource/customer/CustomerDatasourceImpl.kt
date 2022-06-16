package com.example.roomrentalapplication.data.remote.api.datasource.customer

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerPropertyResponse
import com.example.roomrentalapplication.data.remote.exception.apiCall
import javax.inject.Inject

class CustomerDatasourceImpl @Inject constructor(private val apiService: ApiService) :
    CustomerDatasource {
    override suspend fun getCustomerById(customerId: Int): CustomerPropertyResponse {
        return apiCall {
            apiService.getCustomerById(customerId)
        }
    }

    override suspend fun updateCustomerById(
        customerId: Int,
        customerRequest: CustomerProperty
    ): CommonResponse {
        return apiCall {
            apiService.updateCustomerById(customerId, customerRequest)
        }
    }
}
