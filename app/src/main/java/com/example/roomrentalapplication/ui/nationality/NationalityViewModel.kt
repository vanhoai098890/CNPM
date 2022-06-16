package com.example.roomrentalapplication.ui.nationality

import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import com.example.roomrentalapplication.ui.nationality.model.NationalityEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NationalityViewModel @Inject constructor() : BaseViewModel() {

    val nationValues = NationalityEnum.values().toMutableList()
}
