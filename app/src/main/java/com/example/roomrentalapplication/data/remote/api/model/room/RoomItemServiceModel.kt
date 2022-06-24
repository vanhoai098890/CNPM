package com.example.roomrentalapplication.data.remote.api.model.room

enum class RoomItemServiceModel(val serviceId: Int, val serviceName: String, val icon: String) {
    S1(
        1,
        "Free Wifi",
        "faWifi"
    ),
    S2(

        2,
        "Bathtub",
        "faBathtub"
    ),
    S3(

        3,
        "Swimming Pool",
        "faSwimmingPool"
    ),
    S4(

        4,
        "Toilet Paper",
        "faToiletPaper"
    ),
    S5(

        5,
        "Television",
        "faTelevision"
    ),
    S6(

        6,
        "Shower",
        "faShower"
    ),
    S7(

        7,
        "Breakfast",
        "faCoffee"
    ),
    S8(

        8,
        "Bicycle",
        "faBicycle"
    ),
    S9(

        9,
        "Fan",
        "faFan"
    ),
    S10(

        10,
        "Air Conditioner",
        "faSnowflake "
    ),
    S11(

        11,
        "Bed",
        "faBed"
    ),
    S12(

        12,
        "Shuttle Bus",
        "faBus"
    ),
    S13(

        13,
        "Table",
        "faTable"
    ),
    S14(

        14,
        "Chair",
        "faChair"
    ),
    S15(

        15,
        "No Smoking",
        "FaSmokingBan"
    ),
    S16(

        16,
        "Smoking",
        "FaSmoking"
    ),
    S17(

        17,
        "Hot Tub",
        "FaHotTub"
    ),
    S18(

        18,
        "Bar",
        "FaGlassMartiniAlt"
    ),
    S19(

        19,
        "Kitchen",
        "FaUtensils"
    ),
    S20(

        20,
        "Pet-friendly",
        "FaDog"
    )
}

data class RoomItemServiceSmall(val serviceId: Int, val serviceName: String, val icon: String) {
    companion object {
        fun getRoomItemServiceSmall(data: RoomItemServiceModel): RoomItemServiceSmall {
            return RoomItemServiceSmall(
                serviceId = data.serviceId,
                serviceName = data.serviceName,
                icon = data.icon
            )
        }
    }
}
