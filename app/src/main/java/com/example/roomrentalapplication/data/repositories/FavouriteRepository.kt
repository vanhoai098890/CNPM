package com.example.roomrentalapplication.data.repositories

import com.example.roomrentalapplication.data.remote.api.datasource.favourite.FavouriteDatasourceImpl
import com.example.roomrentalapplication.extensions.safeFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepository @Inject constructor(
    private val favouriteDatasourceImpl: FavouriteDatasourceImpl
) {
    fun getFavouriteRooms(customerId: Int) = safeFlow {
        favouriteDatasourceImpl.getFavouriteRooms(customerId)
    }
}
