package com.example.roomrentalapplication.data.remote.api.model.property

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class PropertyResponse(
    @SerializedName("data")
    val data: List<PropertyItem>
) : CommonResponse()

data class PropertyItem(
    var propertyId: Int? = null,
    val customerId: Int? = null,
    val propertyTypeId: Int? = null,
    val imageStorageId: Int? = null,
    val propertyName: String? = null,
    val address: String? = null,
    val city: String? = null,
    val roomQuantity: Int? = null,
    val createDate: String? = null,
    val description: String? = null,
    val rating: Double? = null,
    val images: List<ImageItem>? = null,
    val lat: Double? = null,
    val lon: Double? = null
)

data class ImageItem(
    val id: Int? = null,
    val url: String? = null,
    val thumbnail: String? = null
)
