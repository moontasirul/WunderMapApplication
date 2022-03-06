package com.moon.map_application.data.model

import com.google.gson.annotations.SerializedName

data class QuickRentalRequest(
    @SerializedName("carId") var carId: Int? = null
)
