package com.example.roomrentalapplication.data.remote.api.datasource.sign_in

import com.example.roomrentalapplication.data.remote.ApiService
import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.signin.request.SignInRequestDto
import com.example.roomrentalapplication.data.remote.api.model.signin.response.SignInResponseDto
import com.example.roomrentalapplication.data.remote.api.model.signup.request.SignUpRequestDto
import com.example.roomrentalapplication.data.remote.exception.apiCall
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    LoginDataSource {
    override suspend fun signIn(signInRequestDto: SignInRequestDto): SignInResponseDto {
        return apiCall {
            apiService.signIn(signInRequestDto)
        }
    }

    override suspend fun signUp(signUpRequestDto: SignUpRequestDto): CommonResponse {
        return apiCall {
            apiService.signUp(signUpRequestDto)
        }
    }
}
