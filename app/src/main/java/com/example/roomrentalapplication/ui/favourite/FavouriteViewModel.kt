package com.example.roomrentalapplication.ui.favourite

import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItem
import com.example.roomrentalapplication.data.repositories.FavouriteRepository
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val loginSessionManager: LoginSessionManager,
    private val favouriteRepository: FavouriteRepository
) : BaseViewModel() {
    val stateListFavouriteRoom: MutableStateFlow<MutableList<RoomItem>> = MutableStateFlow(
        mutableListOf()
    )
    val stateVisibleNotFoundItem = MutableStateFlow(
        false
    )

    fun getFavouriteRooms() {
        loginSessionManager.getCustomerLocal()?.customerId?.apply {
            favouriteRepository.getFavouriteRooms(
                this
            ).onSuccess {
                stateListFavouriteRoom.value = it.data as MutableList<RoomItem>
                stateVisibleNotFoundItem.value = it.data.isEmpty()
            }.launchIn(viewModelScope)
        }
    }
}
