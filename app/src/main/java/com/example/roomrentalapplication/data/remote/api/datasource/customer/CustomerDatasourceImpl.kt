package com.example.roomrentalapplication.data.remote.api.datasource.customer

import com.example.roomrentalapplication.data.remote.ApiService
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

}
