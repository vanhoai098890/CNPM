package com.example.roomrentalapplication.ui.personal

import com.example.roomrentalapplication.data.repositories.PersonalRepository
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import com.example.roomrentalapplication.ui.personal.adapter.PersonalFunctionStatic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val personalRepository: PersonalRepository) : BaseViewModel() {
    val listPersonal = MutableStateFlow(
        PersonalFunctionStatic.values()
    )
}
