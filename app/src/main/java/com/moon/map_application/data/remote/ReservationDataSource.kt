package com.moon.map_application.data.remote

import com.moon.map_application.data.model.QuickRentalRequest
import javax.inject.Inject

class ReservationDataSource @Inject constructor(
    private val reservationService: IReservationService
) : BaseDataSource() {
    suspend fun setQuickRental(quickRentalRequest: QuickRentalRequest) =
        getResult { reservationService.getQuickRental(quickRentalRequest) }
}