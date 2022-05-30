package com.example.roomrentalapplication.data

object AppConstant {
    const val PREF_NAME: String = "NRent_pref"

    /**
     * LEVEL of fragment using control back stack smooth
     */
    const val LEVEL_TOP = 0
    const val LEVEL_CONTAINER = 1
    const val LEVEL_TAB = 2
    const val EMPTY = ""

    const val KEY_AUTHORIZATION = "Authorization"
    const val VALUE_AUTHORIZATION_PREFIX = "Bearer"
    const val EMAIL_PATTERN = "\\w+@\\w+(\\.\\w){1,2}"
    const val PHONE_PATTERN = "0\\d{0,10}"
    const val ZERO = 0
}
