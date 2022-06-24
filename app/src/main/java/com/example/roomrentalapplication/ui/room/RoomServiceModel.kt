package com.example.roomrentalapplication.ui.room

import com.example.roomrentalapplication.R

enum class RoomServiceModel(var id: Int, var iconRes: Int = 0) {
    FREE_WIFI(1, R.drawable.ic_wifi),
    BATHTUB(2, R.drawable.ic_bathtub),
    SWIMMING_POOL(3, R.drawable.ic_swimming_pool),
    TOILET_PAPER(4, R.drawable.ic_toilet_paper),
    TELEVISION(5, R.drawable.ic_television),
    SHOWER(6, R.drawable.ic_shower),
    BREAKFAST(7, R.drawable.ic_breakfast),
    BICYCLE(8, R.drawable.ic_bicycle),
    FAN(9, R.drawable.ic_fan),
    AIR_CONDITIONER(10, R.drawable.ic_air_conditioner),
    BED(11, R.drawable.ic_bed),
    SHUTTLE_BUS(12, R.drawable.ic_bus),
    TABLE(13, R.drawable.ic_table),
    CHAIR(14, R.drawable.ic_chair),
    NO_SMOKING(15, R.drawable.ic_no_smoking),
    SMOKING(16, R.drawable.ic_smoking),
    HOT_TUB(17, R.drawable.ic_hot_tub),
    BAR(18, R.drawable.ic_bar),
    KITCHEN(19, R.drawable.ic_kitchen_room),
    PET_FRIENDLY(20, R.drawable.ic_pet_friendly)
}
