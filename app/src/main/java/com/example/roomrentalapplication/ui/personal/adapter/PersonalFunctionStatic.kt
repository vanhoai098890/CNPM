package com.example.roomrentalapplication.ui.personal.adapter

import com.example.roomrentalapplication.R

enum class PersonalFunctionStatic(
    val drawableRes: Int = 0,
    val stringRes: Int = 0,
    val title: Int = 0,
    val isTopPerson: Boolean = false,
) {
    PERSON(isTopPerson = true),
    ACCOUNT(
        title = R.string.v1_account_setting
    ),
    PERSONAL(
        R.drawable.ic_baseline_person_24,
        R.string.v1_edit_profile
    ),
    TRANSACTION(
        R.drawable.ic_transaction_history,
        R.string.v1_transaction
    ),
    BANK(
        R.drawable.ic_bank,
        R.string.v1_bank
    ),
    PASSWORD(
        R.drawable.ic_password,
        R.string.v1_security
    ),

    MANAGEMENT(
        title = R.string.v1_management
    ),
    APPOINTMENT(
        R.drawable.ic_baseline_calendar_month_24,
        R.string.v1_appointment
    ),
    REQUEST(
        R.drawable.ic_request,
        R.string.v1_requests
    ),
    LOGOUT(
        R.drawable.ic_log_out,
        R.string.v1_logout
    )
}
