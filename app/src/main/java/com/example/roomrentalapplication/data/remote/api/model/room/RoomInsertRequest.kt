package com.example.roomrentalapplication.data.remote.api.model.room

data class RoomInsertRequest(
    val roomId: Int? = null,
    val propertyId: Int? = null,
    val price: Price? = null,
    val roomStatusId: Int? = null,
    val imageStorageId: Int? = null,
    val roomName: String? = null,
    val size: String? = null,
    val capacity: String? = null,
    val description: String? = null,
    val bedrooms: Int? = null,
    val images: List<String>? = null,
    val services: List<RoomItemServiceSmall>? = null
) {
    fun isValid(): Boolean {
        if (propertyId == null || propertyId == -1) {
            return false
        }
        if (price == null) {
            return false
        }
        if (roomName.isNullOrBlank() || size.isNullOrBlank() || capacity.isNullOrBlank() || description.isNullOrBlank() || services.isNullOrEmpty()) {
            return false
        }
        return true
    }
}

data class Price(
    val hourPrice: Int,
    val dayPrice: Int,
    val weekPrice: Int,
    val monthPrice: Int
)
