package com.moon.map_application.data.repository

import com.example.example.QuickRentalResponse
import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarInfo
import com.moon.map_application.data.model.QuickRentalRequest
import com.moon.map_application.data.remote.dataSource.CarRemoteDataSource
import com.moon.map_application.data.remote.dataSource.ReservationDataSource
import com.moon.map_application.data.remote.dataSource.baseDataSource.BaseDataSource
import com.moon.map_application.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class AppRepository @Inject constructor(
    private var carDataSource: CarRemoteDataSource,
    private var reservationDataSource: ReservationDataSource
) : BaseDataSource() {

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

    suspend fun setQuickRentalReservation(quickRentalRequest: QuickRentalRequest): Flow<Resource<QuickRentalResponse>> {
        return flow {
            emit(reservationDataSource.setQuickRental(quickRentalRequest))
        }.flowOn(Dispatchers.IO)
    }

}