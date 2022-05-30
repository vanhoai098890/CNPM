package com.example.roomrentalapplication.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import com.example.roomrentalapplication.data.remote.exception.BaseError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    private val loadingState = MutableStateFlow(false)
    private val errorState = MutableStateFlow<BaseError?>(null)

    internal fun loadingState(): StateFlow<Boolean> = loadingState

    internal fun errorState(): StateFlow<BaseError?> = errorState

    internal fun handleError(error: BaseError?) {
        errorState.value = error
    }

    internal fun handleLoading(isLoading: Boolean) {
        loadingState.value = isLoading
    }
}
