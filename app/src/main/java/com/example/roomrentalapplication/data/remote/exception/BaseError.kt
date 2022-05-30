package com.example.roomrentalapplication.data.remote.exception

import com.example.roomrentalapplication.data.remote.api.model.ApiResponseCode
import java.net.HttpURLConnection

/**
 * Define Common Error from server-side
 */
abstract class BaseError : Throwable()

data class NetworkError(
    val code: String?,
    override val message: String?,
    val apiUrl: String?
) : BaseError() {
    val isHttpForbidden: Boolean
        get() = code == HttpURLConnection.HTTP_FORBIDDEN.toString()
}

data class DefaultError(
    val apiErrorCode: ApiResponseCode? = null,
    override val message: String? = null
) : BaseError()

data class DefaultErrorPayload(
    val apiErrorCode: ApiResponseCode? = null,
    override val message: String? = null,
    val data: Any? = null,
) : BaseError()
