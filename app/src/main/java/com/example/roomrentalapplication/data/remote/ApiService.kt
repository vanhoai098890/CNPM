package com.example.roomrentalapplication.data.remote

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerPropertyResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomSavedRequest
import com.example.roomrentalapplication.data.remote.api.model.signin.request.SignInRequestDto
import com.example.roomrentalapplication.data.remote.api.model.signin.response.SignInResponseDto
import com.example.roomrentalapplication.data.remote.api.model.signup.request.SignUpRequestDto
import com.example.roomrentalapplication.data.remote.api.model.status_saved.StatusSavedResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    suspend fun signIn(@Body signInRequestDto: SignInRequestDto): Response<SignInResponseDto>

    @POST("api/auth/register")
    suspend fun signUp(@Body signUpRequestDto: SignUpRequestDto): Response<CommonResponse>

    @GET("api/properties")
    suspend fun getAllProperty(): Response<PropertyResponse>

    @GET("api/properties/{id}")
    suspend fun getPropertyById(@Path("id") id: Int): Response<PropertyItemResponse>

    @GET("api/rooms/priceMonth/{propertyId}")
    suspend fun getRoomsByPropertyId(@Path("propertyId") propertyId: Int): Response<RoomResponse>

    @GET("api/customer/{id}")
    suspend fun getCustomerById(@Path("id") customerId: Int): Response<CustomerPropertyResponse>

    @GET("api/properties/getByCityTypeName")
    suspend fun getRoomsByPropertyWithName(
        @Query("city") cityName: String,
        @Query("typeId") typeId: Int,
        @Query("name") name: String
    ): Response<PropertyResponse>

    @POST("api/saved_rooms")
    suspend fun saveRoom(
        @Body roomSavedRequest: RoomSavedRequest
    ): Response<CommonResponse>

    @HTTP(method = "DELETE", path = "api/saved_rooms", hasBody = true)
    suspend fun deleteRoom(
        @Body roomSavedRequest: RoomSavedRequest
    ): Response<CommonResponse>

    @GET("api/saved_rooms/is_existed")
    suspend fun getStatusSavedRoom(
        @Query("customerId") customerId: Int, @Query("roomId") roomId: Int
    ): Response<StatusSavedResponse>
}
