package com.moon.map_application.data.model

import com.google.gson.annotations.SerializedName

data class CarItem(
    @SerializedName("carId") var carId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("licencePlate") var licencePlate: String? = null,
    @SerializedName("fuelLevel") var fuelLevel: Int? = null,
    @SerializedName("vehicleStateId") var vehicleStateId: Int? = null,
    @SerializedName("vehicleTypeId") var vehicleTypeId: Int? = null,
    @SerializedName("pricingTime") var pricingTime: String? = null,
    @SerializedName("pricingParking") var pricingParking: String? = null,
    @SerializedName("reservationState") var reservationState: Int? = null,
    @SerializedName("isClean") var isClean: Boolean? = null,
    @SerializedName("isDamaged") var isDamaged: Boolean? = null,
    @SerializedName("distance") var distance: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("zipCode") var zipCode: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("locationId") var locationId: Int? = null
)