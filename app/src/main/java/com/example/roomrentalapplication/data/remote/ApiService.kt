package com.example.roomrentalapplication.data.remote

import com.example.roomrentalapplication.data.remote.api.model.CommonResponse
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerProperty
import com.example.roomrentalapplication.data.remote.api.model.customer.CustomerPropertyResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateFurthestResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.DateStatusResponse
import com.example.roomrentalapplication.data.remote.api.model.date_rent.PriceRentResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItem
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyItemResponse
import com.example.roomrentalapplication.data.remote.api.model.property.PropertyResponse
import com.example.roomrentalapplication.data.remote.api.model.received_request.ReceivedRequestItemResponse
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentRequest
import com.example.roomrentalapplication.data.remote.api.model.request_info.RentResponse
import com.example.roomrentalapplication.data.remote.api.model.reservation.ReservationResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomResponse
import com.example.roomrentalapplication.data.remote.api.model.room.RoomSavedRequest
import com.example.roomrentalapplication.data.remote.api.model.signin.request.SignInRequestDto
import com.example.roomrentalapplication.data.remote.api.model.signin.response.SignInResponseDto
import com.example.roomrentalapplication.data.remote.api.model.signup.request.SignUpRequestDto
import com.example.roomrentalapplication.data.remote.api.model.status_saved.StatusSavedResponse
import okhttp3.MultipartBody
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

    @PUT("api/customer/{id}")
    suspend fun updateCustomerById(
        @Path("id") customerId: Int,
        @Body requestCustomer: CustomerProperty
    ): Response<CommonResponse>

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

    @GET("api/reservation/date_status/{roomId}")
    suspend fun getDateStatusByRoomId(
        @Path("roomId") roomId: Int,
        @Query("month") month: Int,
        @Query("year") year: Int,
    ): Response<DateStatusResponse>

    @GET("api/reservation/furthest_valid_date/{roomId}")
    suspend fun getDateFurthestByRoomId(
        @Path("roomId") roomId: Int,
        @Query("from") date: String
    ): Response<DateFurthestResponse>

    @GET("api/price/calcPrice/{id}")
    suspend fun getPrice(
        @Path("id") priceId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): Response<PriceRentResponse>

    @POST("api/reservation")
    suspend fun requestInformation(
        @Body rentRequest: RentRequest
    ): Response<RentResponse>

    @GET("api/reservation/{id}")
    suspend fun getReservation(
        @Path("id") id: Int
    ): Response<ReservationResponse>

    @PUT("api/reservation/reservation_status/{id}")
    suspend fun updateStatusReservation(
        @Path("id") id: Int,
        @Query("reservationStatus") status: Int
    ): Response<CommonResponse>


    @GET("api/reservation/v2/allReservation_by_customer/{id}")
    suspend fun getSendReservation(
        @Path("id") id: Int,
    ): Response<ReceivedRequestItemResponse>

    @GET("api/reservation/v2/allReservation_by_seller/{id}")
    suspend fun getReceivedReservation(
        @Path("id") sellerId: Int,
    ): Response<ReceivedRequestItemResponse>

    @GET("api/customers/{customerId}/saved_rooms")
    suspend fun getFavouriteRoom(
        @Path("customerId") customerId: Int,
    ): Response<RoomResponse>

    @Multipart
    @POST("api/properties")
    suspend fun insertProperty(
        @Part("propertyDto") propertyDto: PropertyItem, @Part files: Array<MultipartBody.Part>
    ): Response<CommonResponse>

}
