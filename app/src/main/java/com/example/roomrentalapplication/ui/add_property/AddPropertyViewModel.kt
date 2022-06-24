package com.example.roomrentalapplication.ui.add_property

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.roomrentalapplication.data.AppConstant
import com.example.roomrentalapplication.data.local.LoginSessionManager
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.repositories.PropertyRepository
import com.example.roomrentalapplication.extensions.bindLoading
import com.example.roomrentalapplication.extensions.getFormatString
import com.example.roomrentalapplication.extensions.onError
import com.example.roomrentalapplication.extensions.onSuccess
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    loginSessionManager: LoginSessionManager
) : BaseViewModel() {

    val imageList: MutableStateFlow<MutableList<Uri?>> = MutableStateFlow(mutableListOf(null))
    val stateValueNameProperty = MutableStateFlow("")
    val statePropertyType = MutableStateFlow(-1)
    val stateDescription = MutableStateFlow("")
    val stateAddress = MutableStateFlow("")
    val stateCity = MutableStateFlow("")
    val stateErrorSubmit = MutableStateFlow(false)
    val stateSuccess = MutableStateFlow(false)
    val stateCustomerProperty: MutableStateFlow<CustomerProperty?> = MutableStateFlow(null)

    init {
        stateCustomerProperty.value = loginSessionManager.getCustomerLocal()
    }

    fun submit(images: List<File>) {
        val temp = mutableListOf<MultipartBody.Part>()
        images.forEach { imageFile ->
            val requestFile = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val multipartBody =
                MultipartBody.Part.createFormData("files", imageFile.name, requestFile)
            temp.add(multipartBody)
        }
        if (stateValueNameProperty.value.isNotBlank()
            && statePropertyType.value != -1
            && stateDescription.value.isNotBlank()
            && stateAddress.value.isNotBlank()
            && stateCity.value.isNotBlank()
            && imageList.value.size > 1
        ) {
            stateCustomerProperty.value?.apply {
                propertyRepository.insertProperty(
                    PropertyItem(
                        propertyName = stateValueNameProperty.value,
                        propertyId = 1000 + Random().nextInt(100),
                        propertyTypeId = statePropertyType.value,
                        description = stateDescription.value,
                        address = stateAddress.value,
                        city = stateCity.value,
                        customerId = this.customerId,
                        createDate = Date().getFormatString(AppConstant.FORMAT_DATE_V2),
                        imageStorageId = 1000 + Random().nextInt(100),
                        roomQuantity = 0,
                        rating = 5.0,
                        lat = 0.0,
                        lon = 0.0,
                        images = mutableListOf()
                    ),
                    temp
                ).bindLoading(this@AddPropertyViewModel).onSuccess {
                    stateSuccess.value = true
                }.onError {
                    stateErrorSubmit.value = true
                }.launchIn(viewModelScope)
            }
        } else {
            stateErrorSubmit.value = true
        }
    }

}
