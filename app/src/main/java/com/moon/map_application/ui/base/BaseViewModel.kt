package com.moon.map_application.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.example.QuickRentalResponse

abstract class BaseViewModel<N : IBaseNavigator> : ViewModel() {

    var _reservationInfo: MutableLiveData<QuickRentalResponse> = MutableLiveData()
    var reservationInfo: LiveData<QuickRentalResponse> = _reservationInfo

    var isLoading = ObservableBoolean(false)
    private lateinit var mNavigator: N

    /**
     * Get Navigator
     */
    protected val navigator: N
        get() {
            return mNavigator
        }

    /**
     * Set Navigator
     */
    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }
}