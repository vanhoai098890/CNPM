package com.example.roomrentalapplication.ui.property_fragment.detail

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.property.ImageItem
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.repositories.CustomerRepository
import com.example.roomrentalapplication.data.repositories.PropertyRepository
import com.example.roomrentalapplication.data.repositories.RoomRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val roomRepository: RoomRepository,
    private val customerRepository: CustomerRepository
) : BaseViewModel() {
    val data: MutableStateFlow<PropertyItem> = MutableStateFlow(PropertyItem())
    val stateCustomerData: MutableStateFlow<CustomerProperty> = MutableStateFlow(CustomerProperty())
    val listImageProperty: MutableStateFlow<List<ImageItem>> = MutableStateFlow(mutableListOf())
    val stateRatingValue: MutableStateFlow<Double> = MutableStateFlow(0.0)

    fun getRoomByPropertyId(id: Int) =
        roomRepository.getRoomByPropertyId(id).bindLoading(this)

    fun getPropertyDetail(id: Int) {
        propertyRepository.getPropertyDetail(id).bindLoading(this).onSuccess {
            it.data.apply {
                customerId?.let { it1 ->
                    getCustomerById(it1)
                }
                data.value = this
            }
            listImageProperty.value = it.data.images ?: mutableListOf()
            stateRatingValue.value = it.data.rating ?: 0.0
        }.launchIn(viewModelScope)
    }

    private fun getCustomerById(id: Int) {
        customerRepository.getCustomerById(id).bindLoading(this).onSuccess {
            stateCustomerData.value = it.data
        }.launchIn(viewModelScope)
    }
}
