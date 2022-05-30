package com.example.roomrentalapplication.data.remote.api.model

import com.example.roomrentalapplication.R
import com.google.gson.annotations.SerializedName

open class CommonResponse(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("message")
    val message: String = "",
)

enum class ApiResponseCode(val code: String, val idStringResource: Int = 0) {
    SUCCESSFUL("200"),
    INVALID_PHONE_NUMBER("INVALID_PHONE_NUMBER"),
    UNAUTHORIZED("E401"),
    E500("E500"),
    E504("E504", R.string.v1_wrong_field),
    E402("E402", R.string.v1_username_is_existed),
}
