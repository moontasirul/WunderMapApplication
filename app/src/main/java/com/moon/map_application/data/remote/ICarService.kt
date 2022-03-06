package com.moon.map_application.data.remote

import com.example.example.QuickRentalResponse
import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarInfo
import com.moon.map_application.data.model.QuickRentalRequest
import com.moon.map_application.data.remote.ApiEndPoint.Companion.CAR_LIST_API
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ICarService {

    @GET(CAR_LIST_API)
    suspend fun getAllCars(): Response<Car>


    @GET("cars/{id}")
    suspend fun getCarInfo(@Path("id") id: Int): Response<CarInfo>


    @POST("default/wunderfleet-recruiting-mobile-dev-quick-rental")
    suspend fun getQuickRental(@Body quickRentalRequest: QuickRentalRequest): Response<QuickRentalResponse>
}