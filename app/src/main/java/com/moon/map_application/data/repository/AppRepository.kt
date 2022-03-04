package com.moon.map_application.data.repository

import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarInfo
import com.moon.map_application.data.remote.BaseDataSource
import com.moon.map_application.data.remote.CarRemoteDataSource
import com.moon.map_application.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class AppRepository @Inject constructor(
    private var carDataSource: CarRemoteDataSource
):BaseDataSource() {

    suspend fun getCar(): Flow<Resource<Car>> {
        return flow {
            emit(carDataSource.getAllCars())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCarDetails(id: Int): Flow<Resource<CarInfo>> {
        return flow {
            emit(carDataSource.getCarInfo(id))
        }.flowOn(Dispatchers.IO)
    }

}