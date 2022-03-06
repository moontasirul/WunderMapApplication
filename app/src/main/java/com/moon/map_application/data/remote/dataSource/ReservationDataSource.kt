package com.moon.map_application.data.remote.dataSource

import com.moon.map_application.data.model.QuickRentalRequest
import com.moon.map_application.data.remote.apiService.IReservationService
import com.moon.map_application.data.remote.dataSource.baseDataSource.BaseDataSource
import javax.inject.Inject

class ReservationDataSource @Inject constructor(
    private val reservationService: IReservationService
) : BaseDataSource() {
    suspend fun setQuickRental(quickRentalRequest: QuickRentalRequest) =
        getResult { reservationService.getQuickRental(quickRentalRequest) }
}