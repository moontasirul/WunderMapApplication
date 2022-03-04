package com.moon.map_application.data.remote

import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ICarService {
    @GET("cars.json")
    suspend fun getAllCars() : Response<Car>

    @GET("cars/carId")
    suspend fun getCarInfo(@Path("id") id: Int): Response<CarItem>
}