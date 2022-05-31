package com.example.roomrentalapplication.ui.room

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.room.RoomSavedRequest
import com.example.roomrentalapplication.data.repositories.RoomRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class RoomDialogViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    loginSessionManager: LoginSessionManager
) : BaseViewModel() {

    val customer = MutableStateFlow<CustomerProperty?>(null)
    val stateErrorCustomerId = MutableStateFlow(false)
    val stateFavourite = MutableStateFlow(false)

    init {
        customer.value = loginSessionManager.getCustomerLocal()
    }

    fun saveRoom(roomId: Int?) {
        customer.value?.apply {
            roomRepository.saveRoom(RoomSavedRequest(customerId ?: 0, roomId ?: 0))
                .bindLoading(this@RoomDialogViewModel)
                .launchIn(viewModelScope)
        } ?: kotlin.run {
            stateErrorCustomerId.value = true
        }
    }

    fun deleteRoomSaved(roomId: Int?) {
        customer.value?.apply {
            roomRepository.deleteRoom(RoomSavedRequest(customerId ?: 0, roomId ?: 0))
                .bindLoading(this@RoomDialogViewModel)
                .launchIn(viewModelScope)
        } ?: kotlin.run {
            stateErrorCustomerId.value = true
        }
    }

    fun getStatusSaved(roomId: Int?) {
        customer.value?.apply {
            roomRepository.getStatusSaved(RoomSavedRequest(customerId ?: 0, roomId ?: 0))
                .bindLoading(this@RoomDialogViewModel)
                .onSuccess {
                    stateFavourite.value = it.data.flag ?: false
                }.launchIn(viewModelScope)
        }
    }
}
