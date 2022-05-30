package com.example.roomrentalapplication.ui.navigationcontainer

import com.example.roomrentalapplication.data.repositories.LoginRepository
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavigationContainerViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {
}
