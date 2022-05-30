package com.example.roomrentalapplication.data.remote.api.model.property

import com.google.gson.annotations.SerializedName

data class PropertyItemResponse(
    @SerializedName("data")
    val data: PropertyItem
)
