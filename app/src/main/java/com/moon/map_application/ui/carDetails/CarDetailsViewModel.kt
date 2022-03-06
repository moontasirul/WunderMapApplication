package com.moon.map_application.ui.carDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moon.map_application.data.model.CarInfo
import com.moon.map_application.data.model.QuickRentalRequest
import com.moon.map_application.data.repository.AppRepository
import com.moon.map_application.ui.base.BaseViewModel
import com.moon.map_application.utils.AppEnum
import com.moon.map_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseViewModel<ICarDetailsNavigator>() {

    private val carId = MutableLiveData<Int>()
    var carTitle = MutableLiveData<String>()
    var carCleanStatus = MutableLiveData<String>()
    var carDamageStatus = MutableLiveData<String>()
    var licencePlate = MutableLiveData<String>()
    var fuelLevel = MutableLiveData<String>()
    var vehicleStateId = MutableLiveData<String>()
    var hardwareId = MutableLiveData<String>()
    var vehicleTypeId = MutableLiveData<String>()
    var pricingTime = MutableLiveData<String>()
    var pricingParking = MutableLiveData<String>()
    var activatedByHardwareStatus = MutableLiveData<String>()
    var locationId = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var zipCode = MutableLiveData<String>()
    var city = MutableLiveData<String>()
    var reservationState = MutableLiveData<String>()
    var damageDescription = MutableLiveData<String>()
    var vehicleTypeImage = MutableLiveData<String>()

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

    fun getCarData(carInfo: Resource<CarInfo>) {
        when (carInfo.status.name) {
            AppEnum.API_CALL_STATUS.SUCCESS.name -> {
                carInfo.data?.let {
                    print(it)
                    isLoading.set(false)
                    prepareUI(it)
                }
            }
            AppEnum.API_CALL_STATUS.ERROR.name -> {
                isLoading.set(false)
                print(carInfo.message)
            }
            AppEnum.API_CALL_STATUS.LOADING.name -> {
                isLoading.set(true)
                print(carInfo.message)
            }
        }

    }

    private fun prepareUI(carInfo: CarInfo) {
        carInfo.carId?.let {
            carId.value = it
        }
        carInfo.title?.let {
            carTitle.value = "Car Title: $it"
        }
        carInfo.isClean?.let {
            carCleanStatus.value = "Clean Status: ${if (it) "Yes" else "No"}"
        }

        carInfo.isDamaged?.let {
            carDamageStatus.value = "Damaged Status: ${if (it) "Yes" else "No"}"
        }

        carInfo.licencePlate?.let {
            licencePlate.value = "Licence Plate: $it"
        }

        carInfo.fuelLevel?.let {
            fuelLevel.value = "Fuel Level: $it"
        }

        carInfo.vehicleStateId?.let {
            vehicleStateId.value = "Vehicle State Id: $it"
        }

        carInfo.hardwareId?.let {
            hardwareId.value = "Hardware Id: $it"
        }
        carInfo.vehicleTypeId?.let {
            vehicleTypeId.value = "Vehicle Type Id: $it"
        }
        carInfo.pricingTime?.let {
            pricingTime.value = "Pricing Time: $it"
        }
        carInfo.pricingParking?.let {
            pricingParking.value = "Pricing Parking: $it"
        }
        carInfo.isActivatedByHardware?.let {
            activatedByHardwareStatus.value = "Activated By Hardware: ${if (it) "yes" else "No"}"
        }
        carInfo.locationId?.let {
            locationId.value = "Location Id: $it"
        }
        carInfo.address?.let {
            address.value = "Address: $it"
        }
        carInfo.zipCode?.let {
            zipCode.value = "Zip Code: $it"
        }
        carInfo.city?.let {
            city.value = "City: $it"
        }
        carInfo.reservationState?.let {
            reservationState.value = "Reservation Sate: $it"
        }
        carInfo.damageDescription?.let {
            damageDescription.value = "Damage Description: $it"
        }
        carInfo.vehicleTypeImageUrl?.let {
            vehicleTypeImage.value = it
        }
    }


    fun onClickQuickRent() {
        isLoading.set(true)
        viewModelScope.launch {
            carId.value?.let {
                repository.setQuickRentalReservation(QuickRentalRequest(it)).collect { response ->
                    when (response.status.name) {
                        AppEnum.API_CALL_STATUS.SUCCESS.name -> {
                            response.data?.let {
                                isLoading.set(false)
                                navigator.showSuccessDialog()
                            }
                        }
                        AppEnum.API_CALL_STATUS.ERROR.name -> {
                            isLoading.set(false)
                            navigator.showFailedDialog()
                            print(response.message)
                        }
                        AppEnum.API_CALL_STATUS.LOADING.name -> {
                            isLoading.set(true)
                        }
                    }
                }
            }
        }
    }
}