package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.remote.api.datasource.sign_in.LoginDataSourceImpl
import com.example.roomrentalapplication.data.remote.api.model.signin.request.SignInRequestDto
import com.example.roomrentalapplication.data.remote.api.model.signup.request.SignUpRequestDto
import com.example.roomrentalapplication.extensions.safeFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val signInDataSourceImpl: LoginDataSourceImpl) {
    fun signIn(signInRequestDto: SignInRequestDto) = safeFlow {
        signInDataSourceImpl.signIn(signInRequestDto)
    }

    fun signUp(signUpRequestDto: SignUpRequestDto) = safeFlow {
        signInDataSourceImpl.signUp(signUpRequestDto)
    }
}
