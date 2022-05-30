package com.example.roomrentalapplication.data.remote

import com.example.roomrentalapplication.data.AppConstant.KEY_AUTHORIZATION
import com.example.roomrentalapplication.data.AppConstant.VALUE_AUTHORIZATION_PREFIX
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.ApiResponseCode
import com.example.roomrentalapplication.data.remote.api.model.refreshtoken.RefreshTokenRequest
import com.example.roomrentalapplication.data.remote.api.model.refreshtoken.RefreshTokenResponse
import com.example.roomrentalapplication.utils.LogUtils
import com.example.roomrentalapplication.utils.eventbus.EventBus
import com.example.roomrentalapplication.utils.eventbus.event_model.EventRequestLogin
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenAuthenticator @Inject constructor(
    private val loginSessionManager: LoginSessionManager,
    private val refreshTokenService: RefreshTokenService,
) : Authenticator {
    private val isHasRefreshToken
        get() = loginSessionManager.isRememberLogin()

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        LogUtils.d(response.toString())
        if (isHasRefreshToken) {
            callApiRefreshToken()?.data?.refreshAuthenticationResult?.let { newTokenResponse ->
                loginSessionManager.updateNewToken(newTokenResponse)
                return response.request.newBuilder()
                    .header(
                        name = KEY_AUTHORIZATION,
                        value = "$VALUE_AUTHORIZATION_PREFIX ${loginSessionManager.getTokenAuthorizationRequest()}"
                    )
                    .build()
            }
        } else {
            showSessionTimeoutDialog()
            loginSessionManager.clearLoginToken()
        }
        return null
    }

    private fun callApiRefreshToken(): RefreshTokenResponse? {
        val refreshToken: String = loginSessionManager.getRefreshToken() ?: return null
        try {
            val response = refreshTokenService.postRefreshToken(
                RefreshTokenRequest(refreshToken)
            ).execute()
            return if (response.isSuccessful && response.body() is RefreshTokenResponse) {
                LogUtils.d(response.body().toString())
                response.body()?.apply {
                    if (this.code == ApiResponseCode.E500.code) {
                        showSessionTimeoutDialog()
                    }
                }
                return response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            LogUtils.e(e.stackTraceToString())
            return null
        }
    }

    private fun showSessionTimeoutDialog() {
        if (loginSessionManager.isLoggedIn()) {
            GlobalScope.launch {
                EventBus.invokeEvent(EventRequestLogin())
            }
        }
    }
}
