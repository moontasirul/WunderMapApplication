package com.moon.map_application.ui.carDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moon.map_application.data.model.CarInfo
import com.moon.map_application.data.repository.AppRepository
import com.moon.map_application.ui.base.BaseViewModel
import com.moon.map_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel() {

    private val carId = MutableLiveData<Int>()
    var carTitle = MutableLiveData<String>()

    private val _carResponse: MutableLiveData<Resource<CarInfo>> = MutableLiveData()
    val response: LiveData<Resource<CarInfo>> = _carResponse

    fun setCarId(id: Int) {
        carId.value = id
    }


    fun fetchCarDetails() {
        viewModelScope.launch {
            carId.value?.let {
                repository.getCarDetails(it).collect { value ->
                    _carResponse.value = value
                }
            }
        }
    }

    fun setCarData(carInfo: CarInfo) {
        carInfo.title?.let {
            carTitle.value = it
        }
    }
}