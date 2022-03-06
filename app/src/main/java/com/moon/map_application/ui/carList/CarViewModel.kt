package com.moon.map_application.ui.carList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.example.QuickRentalResponse
import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarItem
import com.moon.map_application.data.repository.AppRepository
import com.moon.map_application.ui.base.BaseViewModel
import com.moon.map_application.utils.AppEnum
import com.moon.map_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CarViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<ICarListNavigator>() {

    private val _response: MutableLiveData<Resource<Car>> = MutableLiveData()
    val response: LiveData<Resource<Car>> = _response


    var carList: ArrayList<CarItem> = arrayListOf()

    fun fetchCarResponse() = viewModelScope.launch {
        repository.getCar().collect { values ->
            _response.value = values
        }
    }

    fun getCarResponse(response: Resource<Car>) {
        when (response.status.name) {
            AppEnum.API_CALL_STATUS.SUCCESS.name -> {
                response.data?.let {
                    carList.addAll(it)
                }
                isLoading.set(false)
                navigator.addCarMarker()
            }
            AppEnum.API_CALL_STATUS.ERROR.name -> {
                isLoading.set(false)
                print(response.message)
            }
            AppEnum.API_CALL_STATUS.LOADING.name -> {
                isLoading.set(true)
                print(response.message)
            }
        }
    }


    fun getReservationData(): QuickRentalResponse? {
        return repository.getReservationData()
    }

    fun setReservationData() {
        repository.removeReservationData()
    }
}