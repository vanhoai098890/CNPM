package com.example.roomrentalapplication.utils.bindingadapter

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.utils.text_watcher.TextWatcherImpl

@BindingAdapter("textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    if (textWatcher is TextWatcherImpl) {
        textWatcher.refView = editText
        editText.addTextChangedListener(textWatcher)
    } else {
        editText.addTextChangedListener(textWatcher)
    }
}

@BindingAdapter("goneUnless")
fun View.goneUnless(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("setShimmer")
fun View.setShimmer(str: String?) {
    if (str.isNullOrBlank()) {
        setBackgroundColor(context.getColor(R.color.shimmerColor))
    }
}
