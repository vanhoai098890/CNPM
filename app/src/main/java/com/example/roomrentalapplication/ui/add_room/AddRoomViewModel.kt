package com.example.roomrentalapplication.ui.add_room

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.remote.api.model.room.Price
import com.example.roomrentalapplication.data.remote.api.model.room.RoomInsertRequest
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItemServiceModel
import com.example.roomrentalapplication.data.remote.api.model.room.RoomItemServiceSmall
import com.example.roomrentalapplication.data.repositories.PropertyRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.onError
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import com.example.roomrentalapplication.ui.room.RoomServiceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    loginSessionManager: LoginSessionManager
) : BaseViewModel() {

    val serviceList: MutableStateFlow<MutableList<RoomServiceModel>> =
        MutableStateFlow(mutableListOf())
    val stateProperties: MutableStateFlow<MutableList<PropertyItem>> =
        MutableStateFlow(mutableListOf())
    val statePropertiesName: MutableStateFlow<MutableList<String>> =
        MutableStateFlow(mutableListOf())
    val imageList: MutableStateFlow<MutableList<Uri?>> = MutableStateFlow(mutableListOf(null))
    val stateCustomerProperty: MutableStateFlow<CustomerProperty?> = MutableStateFlow(null)
    val stateErrorSubmit = MutableStateFlow(false)
    val stateSuccess = MutableStateFlow(false)
    val stateSetDropMenu = MutableStateFlow(false)
    val statePropertySelected = MutableStateFlow(-1)
    val stateRoomName = MutableStateFlow("")
    val stateRoomSize = MutableStateFlow("")
    val stateCapacity = MutableStateFlow("")
    val stateDescription = MutableStateFlow("")
    val stateHourPrice = MutableStateFlow("")
    val stateDailyPrice = MutableStateFlow("")
    val stateWeeklyPrice = MutableStateFlow("")
    val stateMonthlyPrice = MutableStateFlow("")
    val stateCheckRadio = MutableStateFlow(1)
    val stateDeposit = MutableStateFlow("")

    init {
        val tempList = mutableListOf<RoomServiceModel>()
        tempList.addAll(RoomServiceModel.values())
        tempList.shuffle()
        serviceList.value = tempList.subList(0, 6)
        stateCustomerProperty.value = loginSessionManager.getCustomerLocal()
        getAllProperty()
    }

    private fun getAllProperty() {
        stateCustomerProperty.value?.customerId?.apply {
            propertyRepository.getPropertiesByCustomerId(this).bindLoading(this@AddRoomViewModel)
                .onSuccess {
                    stateProperties.value = it.data as MutableList<PropertyItem>
                    it.data.forEach { propertyItem ->
                        statePropertiesName.value.add(propertyItem.propertyName ?: "")
                    }
                    stateSetDropMenu.value = true
                }.launchIn(viewModelScope)
        }
    }

    fun submit(images: List<File>) {
        val temp = mutableListOf<MultipartBody.Part>()
        images.forEach { imageFile ->
            val requestFile = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val multipartBody =
                MultipartBody.Part.createFormData("files", imageFile.name, requestFile)
            temp.add(multipartBody)
        }
        val tempService = mutableListOf<RoomItemServiceModel>()
        for (roomServiceModel in serviceList.value) {
            tempService.add(RoomItemServiceModel.values()[roomServiceModel.ordinal])
        }
        val roomInsertRequest: RoomInsertRequest
        try {
            roomInsertRequest = RoomInsertRequest(
                propertyId = statePropertySelected.value,
                roomId = null,
                price = Price(
                    stateHourPrice.value.toInt(),
                    stateDailyPrice.value.toInt(),
                    stateWeeklyPrice.value.toInt(),
                    stateMonthlyPrice.value.toInt()
                ),
                roomStatusId = 1,
                roomName = stateRoomName.value,
                size = stateRoomSize.value,
                capacity = stateCapacity.value,
                description = stateDescription.value,
                bedrooms = 1,
                services = tempService.map {
                    RoomItemServiceSmall.getRoomItemServiceSmall(it)
                }
            )
        } catch (e: NumberFormatException) {
            stateErrorSubmit.value = true
            return
        }
        if (roomInsertRequest.isValid()) {
            propertyRepository.insertRoom(
                roomInsertRequest,
                temp
            ).bindLoading(this).onSuccess {
                stateSuccess.value = true
            }.onError {
                stateErrorSubmit.value = true
            }.launchIn(viewModelScope)
        } else {
            stateErrorSubmit.value = true
        }
    }
}
