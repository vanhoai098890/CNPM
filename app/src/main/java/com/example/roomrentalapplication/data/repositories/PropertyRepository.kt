package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.remote.api.datasource.property.PropertyDataSourceImpl
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.extensions.safeFlow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val propertyDataSourceImpl: PropertyDataSourceImpl) {
    fun getAllProperty() = safeFlow {
        propertyDataSourceImpl.getAllProperty()
    }

    fun getProperty(
        city: String = "",
        typeId: Int = 0,
        name: String = ""
    ) = safeFlow {
        propertyDataSourceImpl.getProperty(city, typeId, name)
    }

    fun getPropertyDetail(
        id: Int
    ) = safeFlow {
        propertyDataSourceImpl.getDetailProperty(id)
    }

    fun insertProperty(
        customerId: PropertyItem,
        imagePart: List<MultipartBody.Part>
    ) = safeFlow {
        propertyDataSourceImpl.insertProperty(customerId, imagePart)
    }
}
