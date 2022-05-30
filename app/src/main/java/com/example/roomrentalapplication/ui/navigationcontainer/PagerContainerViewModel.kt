package com.example.roomrentalapplication.ui.navigationcontainer

import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagerContainerViewModel @Inject constructor(
    private val loginSessionManager: LoginSessionManager
) : BaseViewModel() {

    internal fun getToken() = loginSessionManager.getTokenAuthorizationRequest() ?: ""
}
