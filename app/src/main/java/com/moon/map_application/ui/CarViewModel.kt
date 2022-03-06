package com.moon.map_application.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moon.map_application.data.model.Car
import com.moon.map_application.data.model.CarItem
import com.moon.map_application.data.repository.AppRepository
import com.moon.map_application.ui.base.BaseViewModel
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
}