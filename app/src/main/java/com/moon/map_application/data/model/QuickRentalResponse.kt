package com.example.example

import com.google.gson.annotations.SerializedName


data class QuickRentalResponse(

    @SerializedName("reservationId") var reservationId: Int? = null,
    @SerializedName("carId") var carId: Int? = null,
    @SerializedName("cost") var cost: Int? = null,
    @SerializedName("drivenDistance") var drivenDistance: Int? = null,
    @SerializedName("licencePlate") var licencePlate: String? = null,
    @SerializedName("startAddress") var startAddress: String? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("isParkModeEnabled") var isParkModeEnabled: Boolean? = null,
    @SerializedName("damageDescription") var damageDescription: String? = null,
    @SerializedName("fuelCardPin") var fuelCardPin: String? = null,
    @SerializedName("endTime") var endTime: Int? = null,
    @SerializedName("startTime") var startTime: Int? = null

)