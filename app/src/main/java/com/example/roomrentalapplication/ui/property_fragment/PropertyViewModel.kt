package com.example.roomrentalapplication.ui.property_fragment

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.repositories.PropertyRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    BaseViewModel() {
    val allPropertyItem: MutableStateFlow<List<PropertyItem>> = MutableStateFlow(
        listOf(
            PropertyItem(), PropertyItem()
        )
    )

    fun getAllProperty(searchString: String?) {
        if (searchString.isNullOrBlank()) {
            propertyRepository.getAllProperty().bindLoading(this).onSuccess {
                delay(500)
                allPropertyItem.value = it.data
            }.launchIn(viewModelScope)
            return
        }
        propertyRepository.getAllProperty().bindLoading(this).onSuccess {
            delay(500)
            allPropertyItem.value = it.data
        }.launchIn(viewModelScope)
    }

    fun getProperty(city: String = "", typeId: Int = 0, name: String = "") {
        propertyRepository.getProperty(city, typeId, name).bindLoading(this).onSuccess {
            delay(500)
            allPropertyItem.value = it.data
        }.launchIn(viewModelScope)
        return
    }
}
