package com.example.roomrentalapplication.data.remote.api.model.status_saved

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.google.gson.annotations.SerializedName

data class StatusSavedResponse(
    @SerializedName("data")
    val data: StatusSaved
) : CommonResponse()

data class StatusSaved(
    val flag: Boolean?
)
