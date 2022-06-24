package com.example.roomrentalapplication.data.remote.api.model.property

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class PropertyItemResponse(
    @SerializedName("data")
    val data: PropertyItem
)

data class PropertiesItemResponse(
    @SerializedName("data")
    val data: List<PropertyItem>
) : CommonResponse()
