package com.example.roomrentalapplication.ui.personal.adapter

import com.example.roomrentalapplication.data.repositories.PersonalRepository
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogoutDialogViewModel @Inject constructor(private val personalRepository: PersonalRepository) :
    BaseViewModel() {

    fun logoutAction() = personalRepository.logoutAction()
}
