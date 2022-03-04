package com.moon.map_application.data.remote

import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarItem
import com.moon.map_application.data.remote.ApiEndPoint.Companion.CAR_INTO_APR
import com.moon.map_application.data.remote.ApiEndPoint.Companion.CAR_LIST_API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ICarService {
    @GET(CAR_LIST_API)
    suspend fun getAllCars() : Response<Car>

    @GET(CAR_INTO_APR)
    suspend fun getCarInfo(@Path("id") id: Int): Response<CarItem>
}