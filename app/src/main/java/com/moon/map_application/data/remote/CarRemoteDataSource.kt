package com.moon.map_application.data.remote

import javax.inject.Inject

class CarRemoteDataSource  @Inject constructor(
    private val carService: ICarService
): BaseDataSource() {
    suspend fun getAllCars() = getResult { carService.getAllCars() }
    suspend fun getCarInfo(id: Int) = getResult { carService.getCarInfo(id) }
}