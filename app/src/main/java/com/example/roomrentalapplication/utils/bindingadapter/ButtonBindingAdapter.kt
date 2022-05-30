package com.example.roomrentalapplication.utils.bindingadapter

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun Button.visible(status: Boolean) {
    visibility = if (status) View.VISIBLE else View.GONE
}
