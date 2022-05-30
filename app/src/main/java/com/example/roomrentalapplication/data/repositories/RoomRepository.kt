package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.remote.api.datasource.room.RoomDatasourceImpl
import com.example.roomrentalapplication.extensions.safeFlow
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomDatasourceImpl: RoomDatasourceImpl) {
    fun getRoomByPropertyId(propertyId: Int) = safeFlow {
        roomDatasourceImpl.getRoomByPropertyId(propertyId)
    }
}
