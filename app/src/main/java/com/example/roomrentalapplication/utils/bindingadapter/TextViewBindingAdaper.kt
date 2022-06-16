package com.example.roomrentalapplication.utils.bindingadapter

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.ui.splash.sign_in.SignInViewModel
import com.example.roomrentalapplication.ui.splash.sign_up.SignUpViewModel
import com.example.roomrentalapplication.utils.LogUtils


@BindingAdapter("android:text")
fun TextView.bindTextResource(stringResId: Int) {
    try {
        text = context.getString(stringResId)
    } catch (e: Resources.NotFoundException) {
        LogUtils.e(e.stackTraceToString())
    }
}

@BindingAdapter("displayErrorMessage")
fun TextView.displayErrorMessage(msg: Int) {
    visibility = when (msg) {
        SignInViewModel.REQUIRE_FIELD -> {
            text = context.getString(R.string.v1_require_field)
            View.VISIBLE
        }
        SignInViewModel.WRONG_FIELD -> {
            text = context.getString(R.string.v1_wrong_field)
            View.VISIBLE
        }
        SignInViewModel.ERROR_DATABASE -> {
            text = context.getString(R.string.v1_error_save_database)
            View.VISIBLE
        }
        else -> {
            text = AppConstant.EMPTY
            View.GONE
        }
    }
}

@BindingAdapter("displayErrorMessageSignUp")
fun TextView.displayErrorMessageSignUp(msg: Int) {
    visibility = when (msg) {
        SignUpViewModel.REQUIRE_FIELD -> {
            text = context.getString(R.string.v1_require_field_sign_up)
            View.VISIBLE
        }
        SignUpViewModel.WRONG_EMAIL -> {
            text = context.getString(R.string.v1_wrong_email)
            View.VISIBLE
        }
        SignUpViewModel.WRONG_CITIZEN -> {
            text = context.getString(R.string.v1_wrong_citizen_id)
            View.VISIBLE
        }
        SignUpViewModel.WRONG_PHONE -> {
            text = context.getString(R.string.v1_wrong_phone)
            View.VISIBLE
        }
        SignUpViewModel.USERNAME_EXISTED -> {
            text = context.getString(R.string.v1_username_is_existed)
            View.VISIBLE
        }
        SignUpViewModel.REQUIRE_FIELD_NOT_BLANK -> {
            text = context.getString(R.string.v1_require_field_not_blank)
            View.VISIBLE
        }
        else -> {
            text = AppConstant.EMPTY
            View.GONE
        }
    }
}

@BindingAdapter("bindTextUserName")
fun TextView.bindTextUserName(name: String?) {
    var flagUpper = true
    var tempText = ""
    name?.apply {
        forEachIndexed { _, name ->
            if (flagUpper) {
                tempText = "${tempText}${name.toUpperCase()}"
            } else {
                tempText = "${tempText}${name}"
            }
            flagUpper = name == ' '
        }
    }
    text = tempText
}

@BindingAdapter("bindNation")
fun TextView.bindNation(name: String?) {
    var flagUpper = true
    var tempText = ""
    name?.apply {
        forEachIndexed { _, name ->
            if (flagUpper) {
                tempText = "${tempText}${name.toUpperCase()}"
            } else {
                tempText = "${tempText}${name.toLowerCase()}"
            }
            flagUpper = name == ' '
        }
    }
    text = tempText
}

@BindingAdapter("bindTextNationality")
fun TextView.bindTextNationality(nationality: String?) {
    text = if (nationality.isNullOrBlank()) {
        setTextColor(resources.getColor(R.color.gray, null))
        resources.getString(R.string.v1_nationality)
    } else {
        setTextColor(resources.getColor(R.color.blackRussian, null))
        nationality
    }
    bindNation(text.toString())
}

@BindingAdapter("bindHtmlText")
fun TextView.bindHtmlText(name: String?) {
    name?.apply {
        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(name, FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(name)
        }
    }
}
