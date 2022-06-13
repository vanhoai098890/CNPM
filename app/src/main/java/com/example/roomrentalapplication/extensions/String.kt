package com.example.roomrentalapplication.extensions

import com.example.roomrentalapplication.data.AppConstant
import java.util.regex.Pattern

fun String.validateEmail(): Boolean {
    val pattern = Pattern.compile(AppConstant.EMAIL_PATTERN)
    return pattern.matcher(this).find()
}

fun String.validatePhone(): Boolean {
    val pattern = Pattern.compile(AppConstant.PHONE_PATTERN)
    return pattern.matcher(this).find()
}

fun String.validateCitizenId(): Boolean {
    val pattern = Pattern.compile(AppConstant.CITIZEN_ID_PATTERN)
    return pattern.matcher(this).find()
}
