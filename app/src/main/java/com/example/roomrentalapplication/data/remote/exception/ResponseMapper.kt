package com.example.roomrentalapplication.data.remote.exception

import com.example.roomrentalapplication.data.remote.api.model.ApiResponseCode
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.utils.LogUtils
import com.google.gson.Gson
import retrofit2.Response

inline fun <T> apiCall(
    block: () -> Response<T>
): T {
    val response = block()
    val body = response.body()
    return when (response.isSuccessful && body != null) {
        true -> {
            if (body is CommonResponse) {
                if (body.code == ApiResponseCode.SUCCESSFUL.code) {
                    body
                } else {
                    throw response.toError()
                }
            }
            body
        }
        false -> throw response.toError()
    }
}

fun <T> Response<T>.exceptionOnSuccessResponse(): BaseError? {
    if (isSuccessful) {
        this.body()?.let { successResponse ->
            if (successResponse is CommonResponse) {
                return DefaultError(
                    apiErrorCode = ApiResponseCode.valueOf(successResponse.code),
                    message = successResponse.message
                )
            }
        }
    }
    if (code() == ApiResponseCode.UNAUTHORIZED.code.toInt()) {
        return DefaultError(
            apiErrorCode = ApiResponseCode.UNAUTHORIZED
        )
    }
    return null
}

fun <T> Response<T>.toError(): BaseError {
    return try {
        exceptionOnSuccessResponse() ?: NetworkError(
            code = code().toString(),
            message = Gson().fromJson(
                errorBody()?.string() ?: "",
                DefaultError::class.java
            ).message
                ?: "",
            apiUrl = this.raw().request.url.toString()
        )
    } catch (ex: Exception) {
        LogUtils.e(ex.stackTraceToString())
        NetworkError(
            code = code().toString(),
            message = Gson().fromJson(
                errorBody()?.string() ?: "",
                DefaultError::class.java
            ).message
                ?: "",
            apiUrl = this.raw().request.url.toString()
        )
    }
}
