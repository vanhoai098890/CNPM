package com.example.roomrentalapplication.data.remote.api.model.signup.request

import com.example.roomrentalapplication.data.AppConstant

data class SignUpRequestDto(
    var customerName: String = AppConstant.EMPTY,
    var email: String = AppConstant.EMPTY,
    var phoneNumber: String = AppConstant.EMPTY,
    var username: String = AppConstant.EMPTY,
    var password: String = AppConstant.EMPTY,
    var citizenId: String = AppConstant.EMPTY,
    var birthday: String = AppConstant.EMPTY,
    var nationality: String = AppConstant.EMPTY
)
