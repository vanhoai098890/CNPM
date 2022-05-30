package com.example.roomrentalapplication.ui.splash.sign_in

import android.text.Editable
import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.ApiResponseCode
import com.example.roomrentalapplication.data.remote.api.model.signin.request.SignInRequestDto
import com.example.roomrentalapplication.data.remote.exception.DefaultError
import com.example.roomrentalapplication.data.repositories.LoginRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onError
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import com.example.roomrentalapplication.utils.text_watcher.TextWatcherImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginSessionManager: LoginSessionManager,
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    companion object {
        const val NORMAL = 0
        const val REQUIRE_FIELD = 1
        const val WRONG_FIELD = 2
        const val ERROR_DATABASE = 3
    }

    val isRememberLogin = MutableStateFlow(loginSessionManager.isRememberLogin())
    var isPasswordVisible = false
    val username = MutableStateFlow("")
    val usernameWatcherImpl = object : TextWatcherImpl() {
        override fun afterTextChanged(s: Editable?) {
            username.value = s.toString()
            isShowError.value = NORMAL
        }
    }
    val password = MutableStateFlow("")
    val passwordWatcherImpl = object : TextWatcherImpl() {
        override fun afterTextChanged(s: Editable?) {
            password.value = s.toString()
            isShowError.value = NORMAL
        }
    }
    val isShowError = MutableStateFlow(NORMAL)
    val navigateToHomeStatus = MutableStateFlow(false)

    init {
        if (isRememberLogin.value) {
            username.value = loginSessionManager.getUsername()
            password.value = loginSessionManager.getPassword()
        }
    }

    internal fun saveUserInfo() =
        loginSessionManager.saveUserInfo(username.value, password.value)

    internal fun clearRemember() = loginSessionManager.clearUserInfo()

    fun loginAction() {
        if (isShowError.value == NORMAL) {
            if (username.value.length < 6 || password.value.length < 6) {
                isShowError.value = REQUIRE_FIELD
            } else {
                loginRepository.signIn(SignInRequestDto(username.value, password.value))
                    .bindLoading(this)
                    .onError {
                        if (it is DefaultError) {
                            if (it.apiErrorCode?.code == ApiResponseCode.E504.code) {
                                isShowError.value = WRONG_FIELD
                            }
                        }
                    }.onSuccess {
                        loginSessionManager.saveLoginToken(it.loginAuthenticator)
                        navigateToHomeStatus.value = true
                    }.launchIn(viewModelScope)
            }
        }
    }
}